package com.MyblogNew.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ErrorDetailsWithMap {

    private String details;
    private String status;
    private Date date;
    private String message;
    private Map<String , String> all_Errors;

    public ErrorDetailsWithMap(String details , String status , Date date , String message , Map<String , String> map) {
        this.details=details;
        this.status=status;
        this.date=date;
        this.message=message;
        this.all_Errors=map;
    }
}
