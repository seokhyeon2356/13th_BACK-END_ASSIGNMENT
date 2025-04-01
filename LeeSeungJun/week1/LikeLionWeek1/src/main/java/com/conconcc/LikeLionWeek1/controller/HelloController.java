package com.conconcc.LikeLionWeek1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HelloController {
    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> Age = new ArrayList<>();
    ArrayList<Integer> Index = new ArrayList<>();

    @GetMapping("/hello") // /Hello라는 url에서 요청 받으면 실행
    public String hello() {
        return "Hello";
    }
    @GetMapping("/test")
    public String test(@RequestParam String name, @RequestParam String age) {
        this.Name.add(name);
        this.Age.add(age);
        return "Hello "+age +" years old "+name;
    }
    @GetMapping("/test/{name}")
    public String test2(@PathVariable String name) {
        Index.add(name.indexOf(name));

        return name+ " : Hello i am "+Age.get(Index.getFirst())+" years old ";
    }
}