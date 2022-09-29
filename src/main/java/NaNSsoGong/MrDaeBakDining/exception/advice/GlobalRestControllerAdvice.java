package NaNSsoGong.MrDaeBakDining.exception.advice;

import NaNSsoGong.MrDaeBakDining.exception.response.BindingErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BindingErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, Object>> fieldErrors = ex.getFieldErrors().stream()
                .map(e -> {
                    Map<String, Object> ret = new HashMap<>();
                    ret.put("defaultMessage", e.getDefaultMessage());
                    ret.put("field", e.getField());
                    ret.put("rejectedValue", e.getRejectedValue());
                    ret.put("objectName", e.getObjectName());
                    ret.put("codes",e.getCodes());
                    return ret;
                })
                .collect(Collectors.toList());
        List<Map<String, Object>> globalErrors = ex.getGlobalErrors().stream()
                .map(e -> {
                    Map<String, Object> ret = new HashMap<>();
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
}
