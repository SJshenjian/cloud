package online.shenjian.cloud.api.base.service;

import online.shenjian.cloud.client.cloud.dto.doge.DogeDto;
import online.shenjian.cloud.client.cloud.dto.doge.DogeQueryDto;

import java.util.List;

public interface DogeService {

    List<DogeDto> getTop100DogeBalanceHistory(DogeQueryDto dogeQueryDto);
}
