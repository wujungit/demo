package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.ProductInfo;
import com.example.demo.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @GetMapping("/testapi")
    public String testAPI(@RequestParam(value = "name", defaultValue = "Say") String name) {
        logger.info(name);
        return name + ": Hello World";
    }

    @GetMapping("/queryById")
    public ProductInfo queryById(@RequestParam(value = "id", defaultValue = "1") Long id) {
        ProductInfo productInfo = testService.queryById(id);
        logger.debug(JSON.toJSONString(productInfo));
        return productInfo;
    }

}
