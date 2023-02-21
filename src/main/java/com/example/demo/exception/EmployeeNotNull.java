package com.example.demo.exception;

public class EmployeeNotNull extends RuntimeException{
    public EmployeeNotNull(String message){
        super(message);
    }
}
