package com.atguigu.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * MyPasswordEncoder
 * <自定义MD5加密>
 *
 * @author 赵长春
 * @version [版本号, 2021/1/19 10:15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    /***
     * 对明文密码进行加密
     * @param rawPassword
     * @return
     */
    public String encode(CharSequence rawPassword) {

        return privateEncode(rawPassword);

    }

    /***
     * 使用表单加密后的密码和数据库加密的密码进行比较
     * @param rawPassword
     * @param encodePassword
     * @return
     */
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        String formePassword = privateEncode(rawPassword);
        String databasePassword = encodePassword;
        return Objects.equals(formePassword,databasePassword);
    }


    /***
     * 将加密提取成私有方法
     * 对明文密码进行加密
     * @param rawPassword
     * @return
     */
    private  String privateEncode(CharSequence rawPassword){
        try {

//            1、创建messageDigest对象
            String algorithm = "MD5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
//            2、获取rawPassword的字节数组
            byte[] input = ((String) rawPassword).getBytes();
//            3、执行加密
            byte[] output = messageDigest.digest(input);
//            4、转换为16进制数对应的字符
            String encode = new BigInteger(1, output).toString(16).toUpperCase();
            return encode;


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}




