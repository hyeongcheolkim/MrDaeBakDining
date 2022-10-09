package NaNSsoGong.MrDaeBakDining.exception.exception;

public class PersonalInformationException extends BusinessException{
    public PersonalInformationException() {
        super();
    }

    public PersonalInformationException(String message) {
        super(message);
    }

    public PersonalInformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonalInformationException(Throwable cause) {
        super(cause);
    }

    protected PersonalInformationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
