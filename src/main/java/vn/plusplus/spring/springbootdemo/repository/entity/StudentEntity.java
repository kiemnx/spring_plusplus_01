package vn.plusplus.spring.springbootdemo.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter @Setter
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String studentName;

    @Column(name = "id_number")
    private String studentIdNumber;

    //...
    @Column(name = "age")
    private Integer age;

}
