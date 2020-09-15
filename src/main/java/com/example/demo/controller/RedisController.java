package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @GetMapping("/testredis")
    public String testAPI(@RequestParam(value = "name", defaultValue = "Say") String name) {
        return name + ": Hello World";
    }

}
