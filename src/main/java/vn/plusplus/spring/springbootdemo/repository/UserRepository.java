package vn.plusplus.spring.springbootdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.plusplus.spring.springbootdemo.repository.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findOneByUserName(String userName);
    UserEntity findOneById(Integer id);
}
