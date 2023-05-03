package com.sanket.tasksbasicapis.exceptions;

public class InvalidSortTypeException extends RuntimeException {
    public InvalidSortTypeException(String sortType) {
        super(sortType + " is not a valid sort type");
    }
}
