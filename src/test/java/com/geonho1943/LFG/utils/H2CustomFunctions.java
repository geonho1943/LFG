package com.geonho1943.LFG.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;


public class H2CustomFunctions {
    static final Logger LOGGER = LoggerFactory.getLogger(H2CustomFunctions.class);

    public static String sha2(String input, int num) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Timestamp timestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}