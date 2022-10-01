package NaNSsoGong.MrDaeBakDining.error.exception;

public class SignFailException extends BusinessException{
    public SignFailException() {
        super();
    }

    public SignFailException(String message) {
        super(message);
    }

    public SignFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignFailException(Throwable cause) {
        super(cause);
    }

    protected SignFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
