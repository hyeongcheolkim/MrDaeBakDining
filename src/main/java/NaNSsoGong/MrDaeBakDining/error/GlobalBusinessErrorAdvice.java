package NaNSsoGong.MrDaeBakDining.error;

import NaNSsoGong.MrDaeBakDining.error.exception.BusinessException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalBusinessErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessErrorResponse businessException(BusinessException ex){
        return BusinessErrorResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }
}
