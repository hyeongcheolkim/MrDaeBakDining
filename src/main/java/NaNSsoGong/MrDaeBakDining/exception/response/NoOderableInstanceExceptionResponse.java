package NaNSsoGong.MrDaeBakDining.exception.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class NoOderableInstanceExceptionResponse {
    private final String exceptionType = "business";
    private String exceptionName;
    private String message;
    private Map<String, Long> classNameAndId;
}
