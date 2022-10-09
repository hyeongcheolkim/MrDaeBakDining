package NaNSsoGong.MrDaeBakDining.exception.exception;

public class NoProperOrderStatusException extends BusinessException{
    public NoProperOrderStatusException() {
        super();
    }

    public NoProperOrderStatusException(String message) {
        super(message);
    }

    public NoProperOrderStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoProperOrderStatusException(Throwable cause) {
        super(cause);
    }

    protected NoProperOrderStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
