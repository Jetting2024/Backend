package com.choandyoo.jett.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonPropertyOrder({"isSuccess", "message", "result"})
public class ApiResponse<T> {
    private final boolean isSuccess;
    private final String message;
    private final T result;

    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<> (true, "", result);
    }

    public static <T> ApiResponse<T> onFailure(String message) {
        return new ApiResponse<> (false, message, null);
    }
}
