package com.timothy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductEntityException extends RuntimeException {
    public ProductEntityException(String s) {
        super(s);
    }
}

