package NaNSsoGong.MrDaeBakDining.error.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestMethodExceptionResponse {
    private final String exceptionType = "system";
    private String exceptionName;
    private String method;
    private String message;
    private List<String> supportedHttpMethod;
}
