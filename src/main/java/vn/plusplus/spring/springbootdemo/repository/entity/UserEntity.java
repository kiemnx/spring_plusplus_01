package vn.plusplus.spring.springbootdemo.repository.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter @Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private Integer enables;
    @Column(name = "role")
    private String role;

    @Override
    public String toString() {
        return "UserEntity{" +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
