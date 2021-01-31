package vn.plusplus.spring.springbootdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.plusplus.spring.springbootdemo.repository.StudentRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.StudentEntity;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public List<StudentEntity> getAllStudentById(String id, Connection connection) throws Exception{
        String query = "SELECT * FROM student WHERE idNumber='" + id+"' LIMIT 10";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<StudentEntity> students = new ArrayList<>();
        while (rs.next()){
            String name = rs.getString("name");
            //...

            StudentEntity st = new StudentEntity();
            st.setStudentName(name);
            students.add(st);
        }
        return students;
    }
    public List<StudentEntity> getAllStudentByAge(Integer age, Connection connection) throws Exception{
        String query = "SELECT name, id_number FROM student WHERE age=" + age+"";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<StudentEntity> students = new ArrayList<>();
        while (rs.next()){
            String name = rs.getString("name");
            //...

            StudentEntity st = new StudentEntity();
            st.setStudentName(name);
            students.add(st);
        }
        return students;
    }

    @Transactional
    public String changeStudentError(Integer id){
        StudentEntity st = studentRepository.findOneById(id);
        st.setStudentName("PLUSPLUS");
        studentRepository.save(st);
        throw new RuntimeException("Error");
    }


}
