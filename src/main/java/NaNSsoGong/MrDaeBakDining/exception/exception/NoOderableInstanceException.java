package NaNSsoGong.MrDaeBakDining.exception.exception;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class NoOderableInstanceException extends BusinessException{
    Map<String, Long> classNameAndId = new ConcurrentHashMap<>();

    public NoOderableInstanceException(Class c, Long id) {
        super("Orderable의 값이 false이므로 주문할 수 없는 instance입니다");
        classNameAndId.put(c.getSimpleName(), id);
    }

    public NoOderableInstanceException() {
        super();
    }

    public NoOderableInstanceException(String message) {
        super(message);
    }

    public NoOderableInstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoOderableInstanceException(Throwable cause) {
        super(cause);
    }

    protected NoOderableInstanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
