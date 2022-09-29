package NaNSsoGong.MrDaeBakDining.error.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusinessErrorResponse {
    private final String exceptionType = "business";
    private String exceptionName;
    private String message;
}
