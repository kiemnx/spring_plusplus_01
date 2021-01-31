package vn.plusplus.spring.springbootdemo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.plusplus.spring.springbootdemo.repository.entity.StudentEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    List<StudentEntity> findAllByAge(Integer age, Pageable pageable);
    List<StudentEntity> findAllByStudentIdNumber(String id);
    List<StudentEntity> findAllByAge(Integer age);
    List<StudentEntity> findAllByStudentName(String name);

    StudentEntity findOneById(Integer id);
    StudentEntity findOneByStudentNameAndStudentIdNumber(String name, String idNumber);
    List<StudentEntity> findAllByAgeGreaterThan(Integer age);

    //Query with native query return an entity, Indexed Query Parameters
    @Query(nativeQuery = true, value = "SELECT * FROM student WHERE id=?1")
    StudentEntity findStudentByIdNativeIndexed(Integer id);

    //Query with native query return an entity, Named Parameters
    @Query(nativeQuery = true, value = "SELECT * FROM student WHERE id=:Id")
    StudentEntity findStudentByIdNativeNamed(@Param("Id") Integer id);


    //Query with native query return an array object, Indexed Query Parameters
    @Query(nativeQuery = true, value = "SELECT name, age FROM student WHERE id=?1")
    List<Object[]> findStudentByIdNativeIndexed2(Integer id);

    //Query with JPQL, Indexed Query Parameters
    @Query(value = "SELECT st FROM StudentEntity st WHERE id=?1")
    StudentEntity findStudentByIdJPQL(Integer id);

    //Query with JPQL, Named Parameters
    @Query(value = "SELECT st FROM StudentEntity st WHERE id=:Id")
    StudentEntity findStudentByIdJPQLNamed(@Param("Id") Integer id);


    //Query with JPQL, Named Parameters
    @Query(value = "SELECT studentName, age FROM StudentEntity WHERE id=:Id")
    Object[] findStudentByIdJPQLNamed2(@Param("Id") Integer id);


    // Modify with JPQL
    @Transactional
    @Modifying
    @Query("UPDATE StudentEntity st SET st.age = ?1 WHERE st.id = ?2")
    int updateUsingJPQLModify(Integer age, Integer id);

    //Modify with native query
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE student SET age = ?1 WHERE id = ?2")
    int updateUsingNativeModify(Integer age, Integer id);


}
