package userManagement;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    private static final String DECRYPTION_ALGORITHM = "MD5";

    public static String getEncryption(String text){
        try{
            MessageDigest digest = MessageDigest.getInstance(DECRYPTION_ALGORITHM);
            byte[] hashedBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException e){
            System.err.println(e);
            return null;
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }

}
