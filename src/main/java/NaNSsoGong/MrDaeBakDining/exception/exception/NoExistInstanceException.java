package NaNSsoGong.MrDaeBakDining.exception.exception;

public class NoExistInstanceException extends BusinessException {
    public NoExistInstanceException() {
        super();
    }

    public NoExistInstanceException(Class c) {
        super(String.format("존재하지 않는 %s Instance입니다", c.getSimpleName()));
    }

    public NoExistInstanceException(String message) {
        super(message);
    }

    public NoExistInstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoExistInstanceException(Throwable cause) {
        super(cause);
    }

    protected NoExistInstanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
