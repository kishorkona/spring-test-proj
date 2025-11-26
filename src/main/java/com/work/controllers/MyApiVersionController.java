package com.work.controllers;

import com.work.interf.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("v1")
public class MyApiVersionController {
    // This method handles GET /products for version v1
    @GetMapping("/products")
    @ApiVersion("v1")
    public String getProductV1() {
        return "Product data from V1 API.";
    }

    // This method handles GET /products for version v2
    @GetMapping("/products")
    @ApiVersion("v2")
    public String getProductV2() {
        return "Product data from V2 API, now with more details!";
    }
}
