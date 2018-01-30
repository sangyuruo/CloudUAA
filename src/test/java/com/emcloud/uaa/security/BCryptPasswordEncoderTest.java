package com.emcloud.uaa.security;

import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest extends TestCase {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    Logger logger = LoggerFactory.getLogger(BCryptPasswordEncoderTest.class);

    @Test
    public void testCreatePassword() {
        String password = bCryptPasswordEncoder.encode("123456");
//        $2a$10$zWhtz9WTt2h7p7N2abxwTuwX8a8fAe0lRdHn0TwqY60yltMceAK52
//        $2a$10$4.TQZLA9bLhA1xqs6.pW8.iQc0UVA7KX/Y0fGEWSP6kyANltxKcWC
//        $2a$10$8ES3SCrgt9zdjdyHRrUbZeVhK3vCqC9IwDsR8FW5Wg30LRYxkcSpO

        logger.info("password is " + password);
    }
}
