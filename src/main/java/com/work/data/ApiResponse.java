package com.work.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

@Slf4j
@Data
public class ApiResponse {
    private String code;
    private String status;
    private String message;
    private Map<String, Object> data;
    private Object listData;
}
