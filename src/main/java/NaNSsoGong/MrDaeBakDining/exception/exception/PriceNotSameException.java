package NaNSsoGong.MrDaeBakDining.exception.exception;

import lombok.Getter;

@Getter
public class PriceNotSameException extends BusinessException{
    private Integer requestExpectedPrice;
    private Integer actualPrice;
    private static final String defaultMessage = "request의 가격과 서버에서 계산한 actualPrice가 다릅니다";

    public PriceNotSameException(String message, Integer requestExpectedPrice, Integer actualPrice){
        super(message);
        this.requestExpectedPrice = requestExpectedPrice;
        this.actualPrice = actualPrice;
    }

    public PriceNotSameException(Integer requestExpectedPrice, Integer actualPrice){
        super(defaultMessage);
        this.requestExpectedPrice = requestExpectedPrice;
        this.actualPrice = actualPrice;
    }

    public PriceNotSameException() {
        super();
    }

    public PriceNotSameException(String message) {
        super(message);
    }

    public PriceNotSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceNotSameException(Throwable cause) {
        super(cause);
    }

    protected PriceNotSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
