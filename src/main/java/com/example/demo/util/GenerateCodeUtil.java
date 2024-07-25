package com.example.demo.util;

import lombok.SneakyThrows;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class GenerateCodeUtil {
    private GenerateCodeUtil() {}
    // create a otp code
    @SneakyThrows
    public static String generateCode() {
        // method throw NoSuchAlgorithmException
        SecureRandom random = SecureRandom.getInstanceStrong();
        return String.valueOf(random.nextInt(90000)+10000);
    }
}
