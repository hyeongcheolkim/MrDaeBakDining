package NaNSsoGong.MrDaeBakDining.exception;

import NaNSsoGong.MrDaeBakDining.exception.exception.BusinessException;
import NaNSsoGong.MrDaeBakDining.exception.exception.DisabledEntityContainException;
import NaNSsoGong.MrDaeBakDining.exception.exception.PriceNotSameException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import NaNSsoGong.MrDaeBakDining.exception.response.DisabledEntityContainExceptionResponse;
import NaNSsoGong.MrDaeBakDining.exception.response.PriceNotSameExceptionResponse;
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
    public DisabledEntityContainExceptionResponse disableEntityContainException(DisabledEntityContainException ex){
        return DisabledEntityContainExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .disabledEntityContainInfoList(ex.getDisabledEntityContainInfoList())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public PriceNotSameExceptionResponse priceNotSameException(PriceNotSameException ex){
        return PriceNotSameExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .requestExpectedPrice(ex.getRequestExpectedPrice())
                .actualPrice(ex.getActualPrice())
                .build();
    }
}
