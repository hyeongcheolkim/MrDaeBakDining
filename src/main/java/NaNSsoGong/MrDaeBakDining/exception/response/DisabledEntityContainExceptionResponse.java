package NaNSsoGong.MrDaeBakDining.exception.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DisabledEntityContainExceptionResponse {
    private final String exceptionType = "business";
    private String exceptionName;
    private String message;
    private List<DisabledEntityContainInfo> disabledEntityContainInfoList;
}
