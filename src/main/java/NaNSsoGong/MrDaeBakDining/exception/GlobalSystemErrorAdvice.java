package NaNSsoGong.MrDaeBakDining.exception;

import NaNSsoGong.MrDaeBakDining.exception.response.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalSystemErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BindingExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
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

        return BindingExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .fieldErrors(fieldErrors)
                .globalErrors(globalErrors)
                .build();
    }

    @ExceptionHandler
    public RequestMethodExceptionResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        return RequestMethodExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .method(ex.getMethod())
                .message(ex.getMessage())
                .supportedHttpMethod(ex.getSupportedHttpMethods().stream()
                        .map(HttpMethod::name)
                        .collect(Collectors.toList()))
                .build();
    }

    @ExceptionHandler
    public MethodArgumentTypeMismatchExceptionResponse methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return MethodArgumentTypeMismatchExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler
    public ServletRequestBindingExceptionResponse servletRequestBindingException(ServletRequestBindingException ex){
        return ServletRequestBindingExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }
}
