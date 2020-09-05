package xyz.anfun.customer_service.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

public class CryptUtils {
    private String key;

    public CryptUtils() {

    }

    public CryptUtils(String username) {
        // 生成key
        key = getKey(username);
    }

    // 公共密钥aes对象
    private AES aes;

    public AES getAes() {
        synchronized (AES.class){
            if (aes == null){
                aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
            }
        }
        return aes;
    }

    public String decrypt(String data) {
       return getAes().decryptStr(data);
    }

    public String decrypt(String data, String username) {
        AES aesByUser = new AES(Mode.ECB, Padding.PKCS5Padding, getKey(username).getBytes());
        return aesByUser.decryptStr(data);
    }

    public String encrypt(String data) {
        return getAes().encryptHex(data);
    }
    public String encrypt(String data, String username) {
        AES aesByUser = new AES(Mode.ECB, Padding.PKCS5Padding, getKey(username).getBytes());
        return aesByUser.encryptHex(data);
    }

    private static String getKey(String username) {
        return SecureUtil.hmacMd5(username).digestHex(username);
    }
}
