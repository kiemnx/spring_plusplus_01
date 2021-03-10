package vn.plusplus.spring.springbootdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.plusplus.spring.springbootdemo.repository.RoleApiRepository;
import vn.plusplus.spring.springbootdemo.repository.TokenRepository;
import vn.plusplus.spring.springbootdemo.repository.UserRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.RoleApiEntity;
import vn.plusplus.spring.springbootdemo.repository.entity.TokenEntity;
import vn.plusplus.spring.springbootdemo.repository.entity.UserEntity;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SecurityService {

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleApiRepository roleApiRepository;

    public void validUser(String token, String apiPath, String apiMethod){
        TokenEntity tokenEntity = tokenRepository.findOneByToken(token);
        if(tokenEntity == null){
            throw new RuntimeException("Token not valid");
        }
        if(tokenEntity.getExpiredTime().before(new Timestamp(System.currentTimeMillis()))){
            throw new RuntimeException("Token expired");
        }

        String userName = tokenEntity.getUserName();
        UserEntity user = userRepository.findOneByUserName(userName);
        List<RoleApiEntity> roles = roleApiRepository.findAllByRoleName(user.getRole());

        for(RoleApiEntity role : roles){
            if(role.getApiPath().equals(apiPath) && role.getApiMethod().equals(apiMethod)){
                return;
            }
        }
        throw new RuntimeException("Role has no permission");
    }
}
