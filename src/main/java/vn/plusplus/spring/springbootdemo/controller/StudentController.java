package vn.plusplus.spring.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.plusplus.spring.springbootdemo.controller.request.StudentRequest;
import vn.plusplus.spring.springbootdemo.repository.StudentRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.StudentEntity;
import vn.plusplus.spring.springbootdemo.services.StudentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentService studentService;
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

    @PostMapping(value = "/addOne")
    public StudentEntity addStudent(@RequestBody StudentRequest request){
        StudentEntity insertEntity = new StudentEntity();
        insertEntity.setStudentName(request.getStudentName());
        insertEntity.setStudentIdNumber(request.getStudentIdNumber());
        insertEntity.setAge(request.getAge());
        insertEntity = studentRepository.save(insertEntity);
        return insertEntity;
    }

    @PostMapping(value = "/addList")
    public List<StudentEntity> addStudent(@RequestBody List<StudentRequest> requests){
        List<StudentEntity> entities = new ArrayList<>();
        for(StudentRequest rq : requests){
            StudentEntity insertEntity = new StudentEntity();
            insertEntity.setStudentName(rq.getStudentName());
            insertEntity.setStudentIdNumber(rq.getStudentIdNumber());
            insertEntity.setAge(rq.getAge());
            entities.add(insertEntity);
        }
        entities = studentRepository.saveAll(entities);
        return entities;
    }

    @PutMapping(value = "/updateOne/{studentId}")
    public StudentEntity updateOne(@PathVariable(name = "studentId") Integer studentId,
                                        @RequestBody StudentRequest request){
        StudentEntity studentEntity = studentRepository.findOneById(studentId);
        if(studentEntity == null){
            System.out.println("Student with ID " + studentId + " is not existed");
            return null;
        }
        studentEntity.setStudentName(request.getStudentName());
        studentEntity.setStudentIdNumber(request.getStudentIdNumber());
        studentEntity.setAge(request.getAge());
        studentEntity = studentRepository.save(studentEntity);
        return studentEntity;
    }

    @DeleteMapping(value = "/deleteOne/{studentId}")
    public String deleteOne(@PathVariable(name = "studentId") Integer studentId) {
        StudentEntity studentEntity = studentRepository.findOneById(studentId);
        if (studentEntity == null) {
            System.out.println("Student with ID " + studentId + " is not existed");
            return null;
        }
        studentRepository.delete(studentEntity);
        return "DONE";
    }
    @GetMapping(value = "/query/{studentId}")
    public StudentEntity findStudentById(@PathVariable(name = "studentId") Integer id){
        StudentEntity st1 = studentRepository.findStudentByIdNativeIndexed(id);
        List<Object[]> st2 = studentRepository.findStudentByIdNativeIndexed2(id);
        Object[] item1 = st2.get(0);
        String name = String.valueOf(item1[0]);
        Integer age = (int)item1[1];
        System.out.println(name + ":" + age);
        StudentEntity st3 = studentRepository.findStudentByIdJPQL(id);

        StudentEntity st4 = studentRepository.findStudentByIdNativeNamed(id);
        StudentEntity st5 = studentRepository.findStudentByIdJPQLNamed(id);
        Object[] st6 = studentRepository.findStudentByIdJPQLNamed2(id);
        System.out.println(st1);
        return st3;
    }

    @GetMapping(value = "/update/{studentId}")
    public StudentEntity updateStudentById(@PathVariable(name = "studentId") Integer id){
        studentRepository.updateUsingJPQLModify(45, id);
        studentRepository.updateUsingNativeModify(22, id);
        return null;
    }

    @GetMapping(value = "/update-with-transaction")
    public String changeStudentError(){
        return studentService.changeStudentError(1);
    }

    @GetMapping(value = "/findAll/{age}")
    public List<StudentEntity> findAll(@PathVariable(name = "age") Integer age,
                                       @RequestParam(name = "page") Integer pageNum,
                                       @RequestParam(name = "size") Integer pageSize,
                                       @RequestParam(name = "sortBy") String sortBy,
                                       @RequestParam(name = "orderBy") String orderBy){
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        if(orderBy.equals("DESC")) {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, sort);
        List<StudentEntity> response = studentRepository.findAllByAge(age, pageRequest);
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
