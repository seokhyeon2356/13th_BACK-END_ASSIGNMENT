package com.conconcc.LikeLionWeek1.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HelloController {
    ArrayList<User> UserList=new ArrayList<User>();


    @GetMapping("/hello") // /Hello라는 url에서 요청 받으면 실행
    public String hello() {
        return "Hello";
    }
    @GetMapping("/test")
    public String test(@RequestParam String name, @RequestParam int age) {
        User user= new User(name, age);
        UserList.add(user);
        return "Hello "+age +" years old "+name;
    }
    @GetMapping("/test/{name}")
    public String getUserAge(@PathVariable String name) {
        for (User user : UserList) {
            if (user.getName().equals(name)) {
                return String.valueOf(user.getAge());
            }
        }
        return ""; // 사용자를 찾을 수 없을 때는 빈 문자열을 반환
    }

    @Getter
    static class User {
        private final String name;
        private final int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

    }
}