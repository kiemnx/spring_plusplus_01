package vn.plusplus.spring.springbootdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.plusplus.spring.springbootdemo.repository.entity.RoleApiEntity;

import java.util.List;

@Repository
public interface RoleApiRepository extends JpaRepository<RoleApiEntity, Integer> {

    List<RoleApiEntity> findAllByRoleName(String roleName);
}
