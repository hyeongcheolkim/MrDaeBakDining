package NaNSsoGong.MrDaeBakDining.exception.exception;

public class NoExistEntityException extends BusinessException{
    public NoExistEntityException() {
        super();
    }

    public NoExistEntityException(String message) {
        super(message);
    }

    public NoExistEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoExistEntityException(Throwable cause) {
        super(cause);
    }

    protected NoExistEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
