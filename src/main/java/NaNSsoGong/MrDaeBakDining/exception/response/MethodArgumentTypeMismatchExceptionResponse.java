package NaNSsoGong.MrDaeBakDining.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MethodArgumentTypeMismatchExceptionResponse {
    private final String exceptionType = "system";
    private String exceptionName;
    private String message;
}
