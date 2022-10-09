package NaNSsoGong.MrDaeBakDining.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceNotSameExceptionResponse {
    private final String exceptionType = "business";
    private String exceptionName;
    private String message;
    private Integer requestExpectedPrice;
    private Integer actualPrice;
}
