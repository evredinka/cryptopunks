package com.cryptopunks.web;

import com.cryptopunks.service.EntityNotFoundException;
import com.cryptopunks.service.UnsupportedOperationException;
import com.cryptopunks.web.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CryptoPunksExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public final ErrorDTO handleNotFoundException(EntityNotFoundException ex) {
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnsupportedOperationException.class)
    public final ErrorDTO handleNotFoundException(UnsupportedOperationException ex) {
        return new ErrorDTO(ex.getMessage());
    }

}
