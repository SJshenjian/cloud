package online.shenjian.cloud.client.cloud.dto.doge;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class DogeDto {
    private String address;
    private String wallet;
    private List<History> history;

    @Data
    public static class History {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private String date;
        private Long balance;
        private Integer rank;
        private Float percent;
    }
}