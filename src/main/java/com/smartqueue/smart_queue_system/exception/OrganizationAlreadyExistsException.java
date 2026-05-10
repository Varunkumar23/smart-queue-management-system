package com.smartqueue.smart_queue_system.exception;

public class OrganizationAlreadyExistsException extends RuntimeException {
    public OrganizationAlreadyExistsException(String message){
        super(message);
    }
}
