package vn.plusplus.spring.springbootdemo.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Person {
    private String firstName;
    private String lastName;

    public Person() {

    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
