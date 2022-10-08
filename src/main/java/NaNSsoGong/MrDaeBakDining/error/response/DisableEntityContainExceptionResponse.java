package NaNSsoGong.MrDaeBakDining.error.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DisableEntityContainExceptionResponse {
    private final String exceptionType = "business";
    private String exceptionName;
    private String message;
    private Map<String, Long> entityClassNameAndId;
}
