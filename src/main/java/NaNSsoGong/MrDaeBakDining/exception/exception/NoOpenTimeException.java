package NaNSsoGong.MrDaeBakDining.exception.exception;

import lombok.Data;

@Data
public class NoOpenTimeException extends BusinessException {
    private final static String defaultMessage = "운영시간이 아닙니다";
    private Integer requestHour;
    private Integer requestMinute;

    public NoOpenTimeException(Integer requestHour, Integer requestMinute) {
        super(defaultMessage);
        this.requestHour = requestHour;
        this.requestMinute = requestMinute;
    }

    public NoOpenTimeException() {
        super();
    }

    public NoOpenTimeException(String message) {
        super(message);
    }

    public NoOpenTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoOpenTimeException(Throwable cause) {
        super(cause);
    }

    protected NoOpenTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
