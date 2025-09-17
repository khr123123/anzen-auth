package org.khr.anzenauth;

import org.junit.jupiter.api.Test;
import org.khr.anzenauth.utils.TokenUtil;
import org.springframework.boot.test.context.SpringBootTest;

/**
 @author KK
 @create 2025-09-17-10:21
 */
@SpringBootTest
public class JwtTest {


    @Test
    public void testGenerateToken() {
        String token = TokenUtil.generateToken("admin", 1L);
        System.out.println(token);
    }

    @Test
    public void testValidateToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3NTgwNzIxMTcsImV4cCI6MTc1ODA3NTcxNywidXNlck5hbWUiOiJhZG1pbiIsImlhdCI6MTc1ODA3MjExNywidXNlcklkIjoxfQ.lkPAobTt8f-dU5RbKChfZPmjAHaYYlmy1CNG4Qxf8Uk";
        boolean b = TokenUtil.validateToken(token);
        System.out.println(b);
        Object userID = TokenUtil.getClaim(token, "userId");
        System.out.println(userID);
        Object userName = TokenUtil.getClaim(token, "userName");
        System.out.println(userName);
    }
}
