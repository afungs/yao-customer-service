package xyz.anfun.customer_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.anfun.customer_service.util.Response;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@Slf4j
@RestControllerAdvice
public class CustomExtHandler {
    @ExceptionHandler(value=Exception.class)
    Object handleException(Exception e, HttpServletRequest request){
        e.printStackTrace();
        log.error("发证了一个异常 url {}, msg {}",request.getRequestURL(), e.getMessage());
        return  new Response(500, null, "系统内部错误，请稍后再试！", null);
    }

    @ExceptionHandler(value= ValidationException.class)
    Object validationException(Exception e, HttpServletRequest request){
        log.error("发证了一个异常2 url {}, msg {}",request.getRequestURL(), e.getMessage());
        return  new Response(500, null, "系统内部错误，请稍后再试！", null);
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    Object methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return  new Response(406, null, message, null);
    }

}
