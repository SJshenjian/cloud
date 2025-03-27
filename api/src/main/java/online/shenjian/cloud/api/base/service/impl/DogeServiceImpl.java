package online.shenjian.cloud.api.base.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.shenjian.cloud.api.base.model.es.Doge;
import online.shenjian.cloud.api.base.service.DogeService;
import online.shenjian.cloud.client.cloud.dto.doge.DogeDto;
import online.shenjian.cloud.client.cloud.dto.doge.DogeQueryDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DogeServiceImpl implements DogeService {
    // 存储每个地址的最近余额
    private static final Map<String, Long> balanceHistory = new HashMap<>();
    private static final long THRESHOLD = 1_000_000; // 变化阈值：1000万 Doge

    private final ElasticsearchClient elasticsearchClient;

    private static final int TOP_ADDRESS_LIMIT = 100;
    private static final int HISTORY_QUERY_LIMIT = 10000;

    @Override
    public List<DogeDto> getTop100DogeBalanceHistory(DogeQueryDto dogeQueryDto) {
        try {
            // 1. 获取当前排名前100的地址(保持rank顺序)
            List<Doge> topAddresses = getCurrentTop100Addresses();

            // 2. 查询这些地址的历史记录(保持rank顺序)
            return getHistoryForAddresses(topAddresses, dogeQueryDto);
        } catch (IOException e) {
            throw new RuntimeException("查询Doge历史余额失败", e);
        }
    }

    @Override
    @Scheduled(cron = "0 15 19 * * ?")
    public void checkDogeBalance() {
        List<DogeDto> doges = getTop100DogeBalanceHistory(new DogeQueryDto());
        if (doges == null || doges.isEmpty()) {
            System.out.println("未获取到 Dogecoin 数据");
            return;
        }

        for (DogeDto dto : doges) {
            String address = dto.getAddress();
            long currentBalance = dto.getHistory().get(0).getBalance();
            String wallet = dto.getWallet();

            // 初始化或获取上一次余额
            Long previousBalance = balanceHistory.getOrDefault(address, null);

            // 检查余额变化
            if (previousBalance == null) {
//                log.info("[" + java.time.LocalDate.now() + "] 初次记录地址 " + address +
//                        " 的余额: " + currentBalance);
            } else {
                long difference = currentBalance - previousBalance;
                if (Math.abs(difference) >= THRESHOLD) {
                    String action = difference > 0 ? "增加" : "减少";
                    String message = "[" + java.time.LocalDate.now() + "] 警告: 地址 " + address +
                            " 的余额" + action + "了 " + Math.abs(difference) +
                            " Dogecoin (之前: " + previousBalance + ", 现在: " + currentBalance + ")";
                    if (StringUtils.isNotBlank(wallet)) {
                        message += " [重点监控钱包: " + wallet + "]";
                    }
                    log.info(message);
                } else {
//                    log.info("[" + java.time.LocalDate.now() + "] 地址 " + address +
//                            " 的余额变化: " + difference + " (未达提醒阈值)");
                }
            }

            // 更新历史余额
            balanceHistory.put(address, currentBalance);

            // 对于有 wallet 标记的地址，检查历史记录中的变化
            if (StringUtils.isNotBlank(wallet)) {
                List<DogeDto.History> histories = dto.getHistory();
                if (histories != null && !histories.isEmpty()) {
                    long lastHistoryBalance = histories.get(0).getBalance(); // 假设第一个是最近的历史记录
                    for (int i = 1; i < histories.size(); i++) {
                        long olderBalance = histories.get(i).getBalance();
                        long historyDifference = lastHistoryBalance - olderBalance;
                        if (Math.abs(historyDifference) >= THRESHOLD) {
                            String action = historyDifference > 0 ? "减少" : "增加";
                            log.info("[" + histories.get(i).getDate() + "] 历史警告: 地址 " + address +
                                    " 的余额" + action + "了 " + Math.abs(historyDifference) +
                                    " Dogecoin [重点监控钱包: " + wallet + "]");
                        }
                        lastHistoryBalance = olderBalance;
                    }
                }
            }
        }
    }

    /**
     * 获取当前排名前100的地址(按rank升序)
     */
    private List<Doge> getCurrentTop100Addresses() {
        SearchResponse<Doge> response = null;
        try {
            response = elasticsearchClient.search(s -> s
                            .index("doge")
                            .size(TOP_ADDRESS_LIMIT)
                            .sort(so -> so.field(f -> f.field("rank").order(SortOrder.Asc)))
                            .collapse(c -> c.field("address")) // 按地址去重
                            .source(sc -> sc.filter(f -> f.includes("address", "wallet", "rank"))) // 只获取必要字段
                    , Doge.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Doge::getRank)) // 确保排序
                .collect(Collectors.toList());
    }

    /**
     * 根据地址列表查询历史记录
     */
    private List<DogeDto> getHistoryForAddresses(List<Doge> topAddresses, DogeQueryDto queryDto) throws IOException {
        // 提取地址列表(保持rank顺序)
        List<String> addresses = topAddresses.stream()
                .map(Doge::getAddress)
                .collect(Collectors.toList());

        // 构建基础查询
        Query baseQuery = Query.of(q -> q
                .bool(b -> b
                        .must(m -> m.terms(t -> t
                                .field("address")
                                .terms(tv -> tv.value(addresses.stream()
                                        .map(FieldValue::of)
                                        .collect(Collectors.toList())))
                        ))
                )
        );

        // 执行查询
        SearchResponse<Doge> response = elasticsearchClient.search(s -> s
                        .index("doge")
                        .size(HISTORY_QUERY_LIMIT)
                        .query(baseQuery)
                        .sort(so -> so.field(f -> f.field("c_date").order(SortOrder.Asc))) // 按日期排序
                , Doge.class);

        // 按地址分组历史记录
        Map<String, List<Doge>> groupedByAddress = response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Doge::getAddress));

        // 按照原始rank顺序构建结果
        return topAddresses.stream()
                .map(addressDoc -> {
                    List<Doge> historyDocs = groupedByAddress.getOrDefault(addressDoc.getAddress(), Collections.emptyList());
                    return convertToDogeDto(addressDoc, historyDocs);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 转换为DTO对象
     */
    private DogeDto convertToDogeDto(Doge addressDoc, List<Doge> historyDocs) {
        if (historyDocs.isEmpty()) {
            return null;
        }

        DogeDto dto = new DogeDto();
        dto.setAddress(addressDoc.getAddress());
        dto.setWallet(addressDoc.getWallet());

        // 历史记录按日期排序
        dto.setHistory(historyDocs.stream()
                .sorted(Comparator.comparing(Doge::getC_date))
                .map(this::convertToHistoryDto)
                .collect(Collectors.toList()));

        return dto;
    }

    /**
     * 转换为History DTO
     */
    private DogeDto.History convertToHistoryDto(Doge doc) {
        DogeDto.History history = new DogeDto.History();
        history.setDate(doc.getC_date());
        history.setBalance(doc.getBalance());
        history.setRank(doc.getRank());
        history.setPercent(doc.getPercent());
        return history;
    }
}