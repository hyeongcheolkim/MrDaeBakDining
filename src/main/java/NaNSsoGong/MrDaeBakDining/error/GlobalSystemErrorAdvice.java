package NaNSsoGong.MrDaeBakDining.error;

import NaNSsoGong.MrDaeBakDining.error.exception.BusinessException;
import NaNSsoGong.MrDaeBakDining.error.response.BindingErrorResponse;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import NaNSsoGong.MrDaeBakDining.error.response.MethodArgumentTypeMismatchErrorResponse;
import NaNSsoGong.MrDaeBakDining.error.response.RequestMethodErrorResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalSystemErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BindingErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, Object>> fieldErrors = ex.getFieldErrors().stream()
                .map(e -> {
                    Map<String, Object> ret = new ConcurrentHashMap<>();
                    ret.put("defaultMessage", e.getDefaultMessage());
                    ret.put("field", e.getField());
                    ret.put("rejectedValue", e.getRejectedValue());
                    ret.put("objectName", e.getObjectName());
                    return ret;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> globalErrors = ex.getGlobalErrors().stream()
                .map(e -> {
                    Map<String, Object> ret = new ConcurrentHashMap<>();
                    ret.put("defaultMessage", e.getDefaultMessage());
                    ret.put("objectName", e.getObjectName());
                    return ret;
                })
                .collect(Collectors.toList());

        return BindingErrorResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .fieldErrors(fieldErrors)
                .globalErrors(globalErrors)
                .build();
    }

    @ExceptionHandler
    public RequestMethodErrorResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        return RequestMethodErrorResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .method(ex.getMethod())
                .message(ex.getMessage())
                .supportedHttpMethod(ex.getSupportedHttpMethods().stream()
                        .map(HttpMethod::name)
                        .collect(Collectors.toList()))
                .build();
    }

    @ExceptionHandler
    public MethodArgumentTypeMismatchErrorResponse methodArgumentTypeMismatchErrorResponse(MethodArgumentTypeMismatchException ex){
        return MethodArgumentTypeMismatchErrorResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }
}
