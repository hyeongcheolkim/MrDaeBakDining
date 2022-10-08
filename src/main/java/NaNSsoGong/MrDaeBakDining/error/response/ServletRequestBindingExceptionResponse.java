package NaNSsoGong.MrDaeBakDining.error.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServletRequestBindingExceptionResponse {
    private final String exceptionType = "system";
    private String exceptionName;
    private String message;
}
