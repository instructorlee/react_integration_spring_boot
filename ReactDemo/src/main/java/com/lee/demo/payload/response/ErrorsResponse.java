package com.lee.demo.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class ErrorsResponse {
    
    private List<String> errors;

    public ErrorsResponse(List<String> errors) {
        this.errors = errors;
    }
}
