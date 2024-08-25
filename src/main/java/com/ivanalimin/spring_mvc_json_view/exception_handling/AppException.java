package com.ivanalimin.spring_mvc_json_view.exception_handling;

import org.springframework.lang.NonNull;

public class AppException extends RuntimeException {
    public AppException(@NonNull String message) {
        super(message);
    }
}
