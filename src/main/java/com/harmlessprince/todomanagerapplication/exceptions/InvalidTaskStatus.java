package com.harmlessprince.todomanagerapplication.exceptions;

public class InvalidTaskStatus extends RuntimeException{

    public InvalidTaskStatus() {
        super("Invalid tasks status supplied");
    }
}
