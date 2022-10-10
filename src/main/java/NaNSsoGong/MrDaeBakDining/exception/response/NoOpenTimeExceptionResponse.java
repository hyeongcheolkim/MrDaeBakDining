package NaNSsoGong.MrDaeBakDining.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoOpenTimeExceptionResponse {
    private final String exceptionType = "business";
    private String exceptionName;
    private String message;

    private Integer openHour;
    private Integer openMinute;
    private Integer closeHour;
    private Integer closeMinute;

    private Integer requestHour;
    private Integer requestMinute;
}
