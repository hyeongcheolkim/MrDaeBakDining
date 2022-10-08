package NaNSsoGong.MrDaeBakDining.error.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class BindingExceptionResponse {
    private final String exceptionType = "system";
    private String exceptionName;
    private List<Map<String,Object>> fieldErrors = new ArrayList<>();
    private List<Map<String,Object>> globalErrors = new ArrayList<>();
}
