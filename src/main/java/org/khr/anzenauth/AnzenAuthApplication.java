package org.khr.anzenauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.khr.anzenauth.mapper")
public class AnzenAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnzenAuthApplication.class, args);
    }

}
