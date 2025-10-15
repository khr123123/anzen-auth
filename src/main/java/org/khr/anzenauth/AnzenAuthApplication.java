package org.khr.anzenauth;

import jakarta.annotation.PreDestroy;
import org.khr.anzenauth.manager.async.AsyncManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("org.khr.anzenauth.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class AnzenAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnzenAuthApplication.class, args);
    }

    @PreDestroy
    public void destroy() {
        AsyncManager.me().shutdown();
    }

}
