package NaNSsoGong.MrDaeBakDining.exception.exception;

public class DuplicatedFieldValueException extends BusinessException {
    private static final String defaultMessage = "엔티티의 유니크 필드의 값이 중복됩니다";

    public DuplicatedFieldValueException() {
        super(defaultMessage);
    }

    public DuplicatedFieldValueException(String message) {
        super(message);
    }

    public DuplicatedFieldValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedFieldValueException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedFieldValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
