package NaNSsoGong.MrDaeBakDining.domain.member.controller.exception;

import NaNSsoGong.MrDaeBakDining.error.exception.BusinessException;

public class LoginFailException extends BusinessException {
    public LoginFailException() {
        super();
    }

    public LoginFailException(String message) {
        super(message);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailException(Throwable cause) {
        super(cause);
    }

    protected LoginFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
