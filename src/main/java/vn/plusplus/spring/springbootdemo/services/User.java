package vn.plusplus.spring.springbootdemo.services;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {

    private String name;
    private String mssv;

    public User() {
    }

    public User(String name, String mssv) {
        this.name = name;
        this.mssv = mssv;
    }
}
