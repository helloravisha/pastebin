package com.dolby.pastebin.exception;

import com.dolby.pastebin.error.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Centralized handler responsible for capturing the exception.
 *
 * @author ravi shanker katta
 * @since 2021 Sep
 * @version 1.0
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Responsible for handling all the service exceptions due to user data
     * @param serviceException
     * @param request
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Error> handleUserExceptions(ServiceException serviceException, WebRequest request) {
        return new ResponseEntity(serviceException.getError(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Invalid request for query parameter HttpRequestMethodNotSupported : "+ request.getParameter("query") + " : " + ex.getMessage());
        return new ResponseEntity<>(Error.METHOD_NOT_ALLOWED, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Invalid request for query parameter MissingServletRequestParameter : "+ request.getParameter("query") + " : " + ex.getMessage());
        return new ResponseEntity<>(Error.INVALID_INPUT, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Invalid request for query parameter RequestBinding: "+ request.getParameter("query") + " : " + ex.getMessage());
        return new ResponseEntity<>(Error.REQUIRED_FIELD_MISSING, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}