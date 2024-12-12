package com.pvt.enotes.exception;


import java.util.Map;

public class ValidationException extends RuntimeException {

    Map<String,Object> error;
    public ValidationException(Map<String,Object> error){
        super("Validation failed");
        this.error=error;
    }

    public Map<String,Object> getErrors(){
        return error;
    }

}
