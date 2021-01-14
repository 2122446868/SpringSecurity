package com.atguigu.security.config;

import com.atguigu.security.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MyUserDetailsService
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/1/14 17:49]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /***
     * 根据表单提交的用户名查询User对象，并装配角色、权限等信息
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String sql = "SELECT id,loginAcct,userPswd,userName,email,createtime FROM t_admin WHERE loginacct = ?";
        List<Admin> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Admin.class), userName);
        return null;
    }
}
