package vn.plusplus.spring.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.plusplus.spring.springbootdemo.services.AsyncTask;

@RestController
@RequestMapping(value = "/async")
public class TestAPI {

    @Autowired
    AsyncTask asyncTask;

    @GetMapping()
    public String callAsyncTask(){
        asyncTask.doBackGroundTask();
        return "Call success";
    }
}
