package com.scalpels.fountain.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorBody {
    private String dateTime;
    private String exception;
    private String url;
    private String message;
}