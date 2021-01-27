package vn.plusplus.spring.springbootdemo.controller.request;

import lombok.Data;

@Data
public class StudentRequest {
    private String studentName;
    private String studentIdNumber;
    private Integer age;
}
