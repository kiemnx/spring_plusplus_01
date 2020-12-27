package vn.plusplus.spring.springbootdemo.interfaces;

public class Test {
    public static void main(String[] args) {
        Bank bank = new VCB();
        Student student = new Student(bank);
        student.setBank(bank);
    }
}
