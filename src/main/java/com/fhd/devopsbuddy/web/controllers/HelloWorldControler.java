package com.fhd.devopsbuddy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldControler {
    @RequestMapping("/")
    public String sayHello(){
        return "ïndex";
    }
}
