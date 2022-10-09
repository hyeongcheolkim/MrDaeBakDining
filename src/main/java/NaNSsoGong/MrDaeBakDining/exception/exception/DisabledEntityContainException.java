package NaNSsoGong.MrDaeBakDining.exception.exception;

import NaNSsoGong.MrDaeBakDining.exception.response.DisabledEntityContainInfo;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class DisabledEntityContainException extends BusinessException{
    private List<DisabledEntityContainInfo> disabledEntityContainInfoList;
    static private final String defaultMessage = "disable하려는 entity를 참조하고 있는 다른 entity가 존재합니다";

    public DisabledEntityContainException(String message, List<DisabledEntityContainInfo> disabledEntityContainInfoList) {
        super(message);
        this.disabledEntityContainInfoList = disabledEntityContainInfoList;
    }

    public DisabledEntityContainException(List<DisabledEntityContainInfo> disabledEntityContainInfoList) {
        super(defaultMessage);
        this.disabledEntityContainInfoList = disabledEntityContainInfoList;
    }

    public DisabledEntityContainException() {
        super();
    }

    public DisabledEntityContainException(String message) {
        super(message);
    }

    public DisabledEntityContainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisabledEntityContainException(Throwable cause) {
        super(cause);
    }

    protected DisabledEntityContainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
