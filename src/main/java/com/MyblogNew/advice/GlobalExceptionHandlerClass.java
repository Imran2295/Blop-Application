package com.MyblogNew.advice;

import com.MyblogNew.exception.RecordNotFoundException;
import com.MyblogNew.payload.ErrorDetails;
import com.MyblogNew.payload.ErrorDetailsWithMap;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlerClass {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDetailsWithMap validationExceptionHandler(MethodArgumentNotValidException e , WebRequest web){
        Map<String , String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((err)->
                map.put(err.getField() , err.getDefaultMessage()));

        ErrorDetailsWithMap error = new ErrorDetailsWithMap(web.getDescription(false) ,
                HttpStatus.BAD_REQUEST.getReasonPhrase(), new Date() , "Errors Are Below:" , map);
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public ErrorDetails handleRecordNotFound(RecordNotFoundException e , WebRequest web){

        ErrorDetails err = new ErrorDetails(e.getMessage() , new Date() ,
                web.getDescription(false) , HttpStatus.NOT_FOUND.getReasonPhrase() );

        return err;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorDetails handleIntegrityViolation(DataIntegrityViolationException e , WebRequest web){

        ErrorDetails err = new ErrorDetails("Duplicate Title Not Allowed" , new Date() ,
                web.getDescription(false) , HttpStatus.NOT_FOUND.getReasonPhrase() );

        return err;
    }

}
