package br.com.app.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Arrays;

@Component
public class SecurityUtils {

    public String generatePassword(String password) {
        if (StringUtils.isBlank(password)) {
            return null;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.encode(password);
    }

    public boolean verifyPassword(String password, String encodedPassword) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(encodedPassword)) {
            return false;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.matches(password, encodedPassword);
    }

    public byte[] generateHash(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(content.getBytes());
            return md.digest();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean verifyHash(byte[] hash, String content) {
        try {
            byte[] res = generateHash(content);
            return Arrays.equals(hash, res);
        } catch (Exception ex) {
            return false;
        }
    }

}
