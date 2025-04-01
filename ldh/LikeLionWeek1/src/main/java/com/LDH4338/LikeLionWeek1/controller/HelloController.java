package com.LDH4338.LikeLionWeek1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    private List<User> users = new ArrayList<User>();

    @GetMapping("/test")
    public String test(@RequestParam("name") String name, @RequestParam("age") int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        users.add(user);
        return "name은 " + name + " age는 " + age;
    }

    @GetMapping("/test2/{name}")
    public String test2(@PathVariable("name") String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return String.valueOf(user.getAge());
            }
        }
        return "이름을 찾을 수 없습니다.";
    }

    class User {
        String name;
        int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
