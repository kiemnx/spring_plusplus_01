package vn.plusplus.spring.springbootdemo.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role_api")
@Setter @Getter
public class RoleApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "api_method")
    private String apiMethod;

    @Column(name = "api_path")
    private String apiPath;
}
