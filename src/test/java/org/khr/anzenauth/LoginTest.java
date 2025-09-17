package org.khr.anzenauth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 @author KK
 @create 2025-09-17-11:31
 */
@SpringBootTest
public class LoginTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testLogin() {
        String encode = passwordEncoder.encode("khr112020");
        System.out.println(encode);
    }

}
