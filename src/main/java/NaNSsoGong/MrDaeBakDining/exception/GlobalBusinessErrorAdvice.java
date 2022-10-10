package NaNSsoGong.MrDaeBakDining.exception;

import NaNSsoGong.MrDaeBakDining.domain.time.TimeConst;
import NaNSsoGong.MrDaeBakDining.exception.exception.*;
import NaNSsoGong.MrDaeBakDining.exception.response.*;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    NoOderableInstanceExceptionResponse noOderableInstanceException(NoOderableInstanceException ex){
        return NoOderableInstanceExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .classNameAndId(ex.getClassNameAndId())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    NoOpenTimeExceptionResponse noOpenTimeException(NoOpenTimeException ex){
        return NoOpenTimeExceptionResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .openHour(TimeConst.getOpenHour())
                .openMinute(TimeConst.getOpenMinute())
                .closeHour(TimeConst.getCloseHour())
                .closeMinute(TimeConst.getCloseMinute())
                .requestHour(ex.getRequestHour())
                .requestMinute(ex.getRequestMinute())
                .build();
    }
}
