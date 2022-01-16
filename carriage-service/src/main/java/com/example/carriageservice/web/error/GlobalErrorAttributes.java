package com.example.carriageservice.web.error;

import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);

        Throwable error = getError(request);

        if (error instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) error;
            map.put("exception", ex.getClass().getSimpleName());
            map.put("message", ex.getMessage());
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
            return map;
        }

        if (error instanceof NotFound) {
            NotFound ex = (NotFound) error;
            map.put("exception", ex.getClass().getSimpleName());
            map.put("message", ex.getMessage());
            map.put("status", HttpStatus.NOT_FOUND);
            map.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
            return map;
        }

        map.put("exception", "SystemException");
        map.put("message", "System Error , Check logs!");
        map.put("status", "500");
        map.put("error", " System Error ");
        return map;
    }
}
