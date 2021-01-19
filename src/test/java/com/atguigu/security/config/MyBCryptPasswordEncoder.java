package com.atguigu.security.config;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * MyBCryptPasswordEncoder
 * <测试带盐值加密>
 *
 * @author 赵长春
 * @version [版本号, 2021/1/19 11:19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyBCryptPasswordEncoder {

    /***
     * 带盐值加密
     */
    @Test
    public void encoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123123";
        String encode = encoder.encode(rawPassword);
        System.out.println(encode);
//        $2a$10$odgSS4UKdCO3yEOuzeuyeeAqDn7F2aWI.WoOkT7nqLfQ5BHSemjWi
    }


    /***
     * 比较
     */
    @Test
    public void matches() {
        String rawPassword = "123123";
        String databasePassword= "$2a$10$odgSS4UKdCO3yEOuzeuyeeAqDn7F2aWI.WoOkT7nqLfQ5BHSemjWi";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(rawPassword, databasePassword);
        System.out.println(matches ? "一致":"不一致");


    }


}
