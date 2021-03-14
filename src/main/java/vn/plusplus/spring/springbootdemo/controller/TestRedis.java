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
        String key = "token:user" + id;
        String value = UUID.randomUUID().toString();
        System.out.println("Add new key: " + value);
        redisTemplate.opsForValue().set(key, value);
        return "OK";
    }

    @GetMapping(value = "/get-key/{id}")
    public String getKey(@PathVariable(name = "id")String id){
        String key = "token:user" + id;
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }
}


