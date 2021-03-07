package vn.plusplus.spring.springbootdemo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import vn.plusplus.spring.springbootdemo.repository.UserRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.UserEntity;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UnitTesting {

    @Autowired
    private UserRepository repository;

    @Test
    public void comparePassword(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String testPass ="123456";
        UserEntity userEntity = repository.findOneByUserName("kiemnx");
        Boolean result = encoder.matches(testPass, userEntity.getPassword());
        Assert.assertEquals(false, result);
    }
}