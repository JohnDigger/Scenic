package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResult {

    private Object data;
    private String msg;
    private Integer code;
    private int count;
}
