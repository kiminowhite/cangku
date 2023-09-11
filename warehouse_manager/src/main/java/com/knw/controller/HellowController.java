package com.knw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kiminowhite-fy
 * @Description
 * @create 2023-09-07 15:03
 */
@RestController
public class HellowController {
    @GetMapping("/hello")
    public String hello()
    {
        return "hello";
    }
}
