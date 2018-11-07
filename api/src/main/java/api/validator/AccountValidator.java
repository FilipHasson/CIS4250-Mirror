package api.validator;

import api.dao.AccountDAO;
import api.exception.BadRequestException;
import api.exception.UnauthorizedException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AccountValidator {
    //TODO generate meaningful token
    public static final String VALID_TOKEN = "ayyyo";

    public static byte[] hashPassword(String plaintext){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(plaintext.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static byte[] hexStringToByteArray(String s){
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[]data){
        final StringBuilder builder = new StringBuilder();
        for(byte b : data) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String loginRequest(String username, String plaintext){
        if (null == plaintext || plaintext.length() == 0) throw new BadRequestException();
        if (AccountValidator.byteArrayToHexString(AccountValidator.hashPassword(plaintext)).equals(
                AccountValidator.byteArrayToHexString(new AccountDAO().findAccountHash(username)))){
            return VALID_TOKEN;
        }

        System.out.println(plaintext);
//        System.out.println(AccountValidator.hexStringToByteArray(plaintext));
        System.out.println(AccountValidator.byteArrayToHexString(AccountValidator.hashPassword(plaintext)));
        System.out.println(AccountValidator.byteArrayToHexString(new AccountDAO().findAccountHash(username)));
        throw new UnauthorizedException();
    }
}
