package com.example.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseEntity {
    private Boolean success;
    private String errorMsg;
    private Object data;
    private Long total;

    public static HttpResponseEntity ok() {
        return new HttpResponseEntity(true, null, null, null);
    }

    public static HttpResponseEntity ok(Object data) {
        return new HttpResponseEntity(true, null, data, null);
    }

    public static HttpResponseEntity ok(List<?> data, Long total) {
        return new HttpResponseEntity(true, null, data, total);
    }

    public static HttpResponseEntity fail(String errorMsg) {
        return new HttpResponseEntity(false, errorMsg, null, null);
    }
}
