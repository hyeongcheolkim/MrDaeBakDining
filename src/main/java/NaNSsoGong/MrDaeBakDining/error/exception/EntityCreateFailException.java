package NaNSsoGong.MrDaeBakDining.error.exception;

public class EntityCreateFailException extends BusinessException{
    private final static String defaultMessage = "이미 같은 이름의 Entity가 존재합니다";

    public EntityCreateFailException() {
        super(defaultMessage);
    }

    public EntityCreateFailException(String message) {
        super(message);
    }

    public EntityCreateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityCreateFailException(Throwable cause) {
        super(cause);
    }

    protected EntityCreateFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
