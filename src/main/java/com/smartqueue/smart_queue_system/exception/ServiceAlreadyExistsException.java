package com.smartqueue.smart_queue_system.exception;

public class ServiceAlreadyExistsException extends RuntimeException {

    public ServiceAlreadyExistsException(String message) {
        super(message);
    }
}