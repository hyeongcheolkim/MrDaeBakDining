package NaNSsoGong.MrDaeBakDining.domain.member.controller;

import NaNSsoGong.MrDaeBakDining.exception.response.RuntimeErrorResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.exception.LoginFailException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberRestControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public RuntimeErrorResponse loginFailException(LoginFailException ex){
        return RuntimeErrorResponse.builder()
                .exceptionName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }
}
