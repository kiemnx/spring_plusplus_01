package vn.plusplus.spring.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		System.out.println("Starting Spring Boot App");
		SpringApplication.run(SpringBootDemoApplication.class, args);
		System.out.println("Started Spring Boot App");
	}
}
