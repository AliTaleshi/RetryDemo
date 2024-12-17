package com.example.RetryDemo.controller;

import com.example.RetryDemo.service.RetryableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retry")
public class RetryableController {

    @Autowired
    private RetryableService retryableService;

    @GetMapping
    public HttpStatus testRetry() {
        return retryableService.retryableMethod();
    }
}
