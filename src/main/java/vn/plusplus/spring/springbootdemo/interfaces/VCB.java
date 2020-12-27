package vn.plusplus.spring.springbootdemo.interfaces;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class VCB implements Bank{
    private String accountNumber;
    private String name;
    private String issueDate;
    private String expiredDate;

    public VCB() {
    }

    public VCB(String accountNumber, String name, String issueDate, String expiredDate) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.issueDate = issueDate;
        this.expiredDate = expiredDate;
    }
}
