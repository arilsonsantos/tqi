package br.com.tqi.test.development.exceptions;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

/**
 * FeignExceptionsHandler
 */
@RestControllerAdvice
public class FeignExceptionHandler {

    @ExceptionHandler(FeignException.BadRequest.class)
    public Map<String, Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return new GsonJsonParser().parseMap(e.contentUTF8());
    }
}