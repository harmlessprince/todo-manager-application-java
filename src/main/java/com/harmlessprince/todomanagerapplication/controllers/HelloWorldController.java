package com.harmlessprince.todomanagerapplication.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {


    @GetMapping(path = "/hello")
    public String hello(Model model){
        model.addAttribute("message", "hello world");
        return "helloworld";
    }
    @GetMapping(path = "/csjs")
    public String csjs(Model model){
        model.addAttribute("message", "Css and Jss file");
        return "csjsdemo";
    }

    @GetMapping(path = "/bootstrap")
    public String bootstrap(Model model){
        model.addAttribute("message", "Css and Jss file");
        return "bootstrap";
    }
}
