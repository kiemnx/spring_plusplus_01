package vn.plusplus.spring.springbootdemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.ApiEntity;

public interface ApiRepository extends JpaRepository<ApiEntity, Integer> {
    ApiEntity findOneById(Integer id);
}
