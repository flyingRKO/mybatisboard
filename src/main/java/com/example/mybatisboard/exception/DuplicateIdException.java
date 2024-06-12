package com.example.mybatisboard.exception;

public class DuplicateIdException extends RuntimeException{
    public DuplicateIdException(String msg){
        super(msg);
    }
}
