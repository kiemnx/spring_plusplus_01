package vn.plusplus.spring.springbootdemo.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_token")
@Getter @Setter
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_token")
    private String token;

    @Column(name = "expired_time")
    private Timestamp expiredTime;
}
