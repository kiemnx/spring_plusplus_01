package vn.plusplus.spring.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/redis")
public class TestRedis {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping(value = "/add-new-key/{id}")
    public String addNewKey(@PathVariable(name = "id")String id){
        String value = "user" + id;
        String key = UUID.randomUUID().toString();
        System.out.println("Add new key: " + value);
        redisTemplate.opsForValue().set(key, value);
        return "OK";
    }

    @GetMapping(value = "/get-key/{token}")
    public String getKey(@PathVariable(name = "token")String token){
        System.out.println("Start time: " + System.currentTimeMillis());
        String value = (String) redisTemplate.opsForValue().get(token);
        System.out.println("End time: " + System.currentTimeMillis());
        return value;
    }
}


