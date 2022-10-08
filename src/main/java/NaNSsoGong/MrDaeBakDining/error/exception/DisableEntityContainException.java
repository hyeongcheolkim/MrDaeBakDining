package NaNSsoGong.MrDaeBakDining.error.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class DisableEntityContainException extends BusinessException{
    private Map<String, Long> entityClassNameAndId;
    static private final String defaultMessage = "disable하려는 entity를 참조하고 있는 다른 entity가 존재합니다";

    public DisableEntityContainException(String message, Map<String, Long> entityClassNameAndId) {
        super(message);
        this.entityClassNameAndId = entityClassNameAndId;
    }

    public DisableEntityContainException(Map<String, Long> entityClassNameAndId) {
        super(defaultMessage);
        this.entityClassNameAndId = entityClassNameAndId;
    }

    public DisableEntityContainException() {
        super();
    }

    public DisableEntityContainException(String message) {
        super(message);
    }

    public DisableEntityContainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisableEntityContainException(Throwable cause) {
        super(cause);
    }

    protected DisableEntityContainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
