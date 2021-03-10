package vn.plusplus.spring.springbootdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.plusplus.spring.springbootdemo.repository.entity.TokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    TokenEntity findOneByToken(String token);
    TokenEntity findOneByUserName(String userName);
}
