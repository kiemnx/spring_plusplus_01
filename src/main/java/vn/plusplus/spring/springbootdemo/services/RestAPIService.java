package vn.plusplus.spring.springbootdemo.services;

import org.springframework.stereotype.Service;
import vn.plusplus.spring.springbootdemo.controller.response.GetResponse;

@Service
public class RestAPIService {
    public GetResponse getResponse(){
        GetResponse response = new GetResponse();
        //Valid input
        // Query DB
        // Build data
        return response;
    }
}
