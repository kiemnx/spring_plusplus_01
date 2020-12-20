package vn.plusplus.spring.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1.0")
public class HomeController {

    @GetMapping(value = "/get-news-detail") // CRUD create = POST, read = GET, update = PUT, delete= DELETE
    public String getNewsDetail(){
        System.out.println("Received request from client");
        return "This is detail news";
    }

    @RequestMapping(value = "/get-2", method = RequestMethod.GET)
    public String getAPI2(){
        return "OK";
    }
}
