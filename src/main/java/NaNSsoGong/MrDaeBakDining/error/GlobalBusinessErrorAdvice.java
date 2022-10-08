package NaNSsoGong.MrDaeBakDining.error;

import NaNSsoGong.MrDaeBakDining.error.exception.BusinessException;
import NaNSsoGong.MrDaeBakDining.error.exception.DisableEntityContainException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessExceptionResponse;
import NaNSsoGong.MrDaeBakDining.error.response.DisableEntityContainExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalBusinessErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessExceptionResponse businessException(BusinessException ex){
        return BusinessExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public DisableEntityContainExceptionResponse disableEntityContainException(DisableEntityContainException ex){
        return DisableEntityContainExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .entityClassNameAndId(ex.getEntityClassNameAndId())
                .build();
    }
}
