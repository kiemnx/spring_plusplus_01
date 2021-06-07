package vn.plusplus.spring.springbootdemo.interceptor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Payload {
    private Integer userId;

    private String accessToken;
/*
    private UserTypeEnum userType;

    private List<String> roles;

    private List<String> permissions;*/
}
