package vn.plusplus.spring.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.plusplus.spring.springbootdemo.controller.request.LoginRequest;
import vn.plusplus.spring.springbootdemo.repository.TokenRepository;
import vn.plusplus.spring.springbootdemo.repository.UserRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.TokenEntity;
import vn.plusplus.spring.springbootdemo.repository.entity.UserEntity;
import vn.plusplus.spring.springbootdemo.services.SecurityService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
public class SecurityManullyController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    TokenRepository tokenRepository;

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginRequest request){
        UserEntity userEntity = userRepository.findOneByUserName(request.getUser());
        if(userEntity == null){
            throw new RuntimeException("User is not existed");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Boolean check = encoder.matches(request.getPass(), userEntity.getPassword());
        if(check.equals(false)){
            throw new RuntimeException("Pass is not correct");
        }
        long deltaTime = 60*1000;
        String token = UUID.randomUUID().toString();
        TokenEntity tokenEntity = tokenRepository.findOneByUserName(userEntity.getUserName());
        if(tokenEntity == null){
            tokenEntity = new TokenEntity();
        }
        tokenEntity.setToken(token);
        System.out.println(token);
        tokenEntity.setUserName(userEntity.getUserName());
        tokenEntity.setExpiredTime(new Timestamp(System.currentTimeMillis() + deltaTime));
        tokenRepository.save(tokenEntity);
        return token;
    }

    @GetMapping(value = "/userinfo/{id}")
    public UserEntity getUserById(@RequestHeader(name = "token") String token,
                                  @PathVariable(name = "id") Integer id){
        securityService.validUser(token, "/userinfo/{id}", "GET");
        return userRepository.findOneById(id);
    }

    @PostMapping(value = "/file/upload")
    public String uploadFile(@RequestHeader(name = "token") String token){
        securityService.validUser(token, "/file/upload", "POST");
        return "OK";
    }

    @GetMapping(value = "/userlist")
    public List<UserEntity> findAllUser(@RequestHeader(name = "token") String token){
        securityService.validUser(token, "/userlist", "GET");
        return userRepository.findAll();
    }

}
