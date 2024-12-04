package com.example.pethealth.exception;

import org.springframework.boot.context.config.ConfigDataNotFoundException;

public class AppointmentException extends RuntimeException{
    public AppointmentException(String erroMessage){
        super(erroMessage);
    }
}
