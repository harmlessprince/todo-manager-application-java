package com.harmlessprince.todomanagerapplication.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ApiResponseHelper<T> {
    private String message;
    private T data;

    private Map<String, Object> meta;

    private Boolean success;

    public String getMessage() {
        return message;
    }


    public ApiResponseHelper() {
        this.message = "Success";
        this.data = null;
        this.meta = new HashMap<>();
        this.success = true;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static <T> ApiResponseHelper<T> success(T data) {
        ApiResponseHelper<T> response = new ApiResponseHelper<>();
        response.setData(data);
        return response;
    }


    public static <T> ApiResponseHelper<T> success(T data, String message) {
        ApiResponseHelper<T> response = new ApiResponseHelper<>();
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponseHelper<T> success(T data, String message, Map<String, Object> meta) {
        ApiResponseHelper<T> response = new ApiResponseHelper<>();
        response.setMessage(message);
        response.setData(data);
        response.setMeta(meta);
        return response;
    }

    public static <T> ApiResponseHelper<T> error(String message) {
        ApiResponseHelper<T> response = new ApiResponseHelper<>();
        response.setMessage(message);
        return response;
    }
}
