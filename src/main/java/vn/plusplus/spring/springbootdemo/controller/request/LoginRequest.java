package vn.plusplus.spring.springbootdemo.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String user;
    private String pass;
}
