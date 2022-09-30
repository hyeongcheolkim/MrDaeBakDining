package NaNSsoGong.MrDaeBakDining.error.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ServletRequestBindingError {
    private final String exceptionType = "system";
    private String exceptionName;
    private String message;
}
