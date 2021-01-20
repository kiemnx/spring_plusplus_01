package vn.plusplus.spring.springbootdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.plusplus.spring.springbootdemo.repository.entity.StudentEntity;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    List<StudentEntity> findAllByStudentIdNumber(String id);
    List<StudentEntity> findAllByAge(Integer age);
    List<StudentEntity> findAllByStudentName(String name);

    StudentEntity findOneById(Integer id);
    StudentEntity findOneByStudentNameAndStudentIdNumber(String name, String idNumber);
    List<StudentEntity> findAllByAgeGreaterThan(Integer age);

    @Query(nativeQuery = true, value = "SELECT * FROM student WHERE id=?1")
    StudentEntity findStudentById(Integer id);
}
