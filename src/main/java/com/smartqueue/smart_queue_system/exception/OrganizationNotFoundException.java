package com.smartqueue.smart_queue_system.exception;

public class OrganizationNotFoundException extends RuntimeException{
    public OrganizationNotFoundException(String message){
        super(message);
    }
}
