package NaNSsoGong.MrDaeBakDining.error.exception;

public class MinusQuantityException extends BusinessException{
    public MinusQuantityException() {
        super();
    }

    public MinusQuantityException(String message) {
        super(message);
    }

    public MinusQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinusQuantityException(Throwable cause) {
        super(cause);
    }

    protected MinusQuantityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
