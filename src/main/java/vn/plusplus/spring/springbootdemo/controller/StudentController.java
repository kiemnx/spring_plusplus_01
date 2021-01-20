package vn.plusplus.spring.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.plusplus.spring.springbootdemo.repository.StudentRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.StudentEntity;
import vn.plusplus.spring.springbootdemo.services.StudentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

//    @Autowired
//    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;


    Connection connection;

    @GetMapping(value = "/{age}")
    public List<StudentEntity> findStudent(@PathVariable(name = "age") Integer age) throws Exception{
        List<StudentEntity> response = studentRepository.findAllByAge(age);
        return response;
    }

    @GetMapping(value = "/id/{idNumber}")
    public List<StudentEntity> findStudentById(@PathVariable(name = "idNumber") String idNumber) throws Exception{
        List<StudentEntity> response = studentRepository.findAllByStudentIdNumber(idNumber);
        return response;
    }


    /*Connection getConnection(){
        if(connection == null){
            try {
                String databaseUrl = "jdbc:mysql://localhost:3306/student?characterEncoding=utf8";
                String user = "root";
                String pass = "1234";
                connection = DriverManager
                        .getConnection(databaseUrl, user, pass);
                System.out.println("SQL Connection to database established!");
                return connection;
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console" + e);
                return null;
            }
        }
        return connection;
    }*/
}
