package org.khr.anzenauth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 @author KK
 @create 2025-09-24-16:10
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void test() {
        System.out.println("redisTemplate = " + redisTemplate);
    }

}
