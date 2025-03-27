package online.shenjian.cloud.api.base.service;

import online.shenjian.cloud.client.cloud.dto.doge.DogeDto;
import online.shenjian.cloud.client.cloud.dto.doge.DogeQueryDto;

import java.util.List;

public interface DogeService {

    List<DogeDto> getTop100DogeBalanceHistory(DogeQueryDto dogeQueryDto);

    /**
     * 大户余额增加或减少1千万，则触发报警
     */
    void checkDogeBalance();
}
