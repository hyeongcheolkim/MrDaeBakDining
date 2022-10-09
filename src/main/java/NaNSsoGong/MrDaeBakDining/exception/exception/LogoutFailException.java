package NaNSsoGong.MrDaeBakDining.exception.exception;

import NaNSsoGong.MrDaeBakDining.exception.exception.BusinessException;

public class LogoutFailException extends BusinessException {
    public LogoutFailException() {
        super();
    }

    public LogoutFailException(String message) {
        super(message);
    }

    public LogoutFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogoutFailException(Throwable cause) {
        super(cause);
    }

    protected LogoutFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
