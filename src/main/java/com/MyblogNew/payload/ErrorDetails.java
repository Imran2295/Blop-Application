package com.MyblogNew.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorDetails {

    private String message;
    private Date date;
    private String details;
    private String status;

    public ErrorDetails(String message , Date date , String details , String status) {
        this.message = message;
        this.date = date;
        this.details = details;
        this.status = status;
    }
}
