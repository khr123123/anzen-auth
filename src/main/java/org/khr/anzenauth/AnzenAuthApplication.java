package org.khr.anzenauth;

import jakarta.annotation.PreDestroy;
import org.khr.anzenauth.manager.async.AsyncManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.khr.anzenauth.mapper")
public class AnzenAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnzenAuthApplication.class, args);
    }

    @PreDestroy
    public void destroy() {
        AsyncManager.me().shutdown();
    }
}
