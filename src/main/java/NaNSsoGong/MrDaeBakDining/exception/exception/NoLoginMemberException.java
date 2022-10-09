package NaNSsoGong.MrDaeBakDining.exception.exception;

public class NoLoginMemberException extends BusinessException{
    public NoLoginMemberException() {
        super();
    }

    public NoLoginMemberException(String message) {
        super(message);
    }

    public NoLoginMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoLoginMemberException(Throwable cause) {
        super(cause);
    }

    protected NoLoginMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
