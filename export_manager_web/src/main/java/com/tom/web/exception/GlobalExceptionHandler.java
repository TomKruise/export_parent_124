package com.tom.web.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e) {
        System.out.println(e.getMessage());
        return "error";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handlerUnauthorizedException(UnauthorizedException ue) {
        return "forward:/unauthorized.jsp";
    }
}
