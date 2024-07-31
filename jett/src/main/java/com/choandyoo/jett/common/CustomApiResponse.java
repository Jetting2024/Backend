package com.choandyoo.jett.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonPropertyOrder({"isSuccess", "message", "result"})
public class CustomApiResponse<T> {
    private final boolean isSuccess;
    private final String message;
    private final T result;

    public static <T> CustomApiResponse<T> onSuccess(T result) {
        return new CustomApiResponse<> (true, "", result);
    }

    public static <T> CustomApiResponse<T> onFailure(String message) {
        return new CustomApiResponse<> (false, message, null);
    }
}
