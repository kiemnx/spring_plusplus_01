package vn.plusplus.spring.springbootdemo.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private String name;
    private String mssv;
    private Bank bank;
    // private VCB vcb;
    // private SHB shb;

    public Student() {
    }

    public Student(Bank bank) {
       this.bank = bank;
    }
}
