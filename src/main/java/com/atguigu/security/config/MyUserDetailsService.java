package com.atguigu.security.config;

import com.atguigu.security.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * MyUserDetailsService
 * <自定义MyUserDetailsService完成数据库用户登录>
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
    public UserDetails loadUserByUsername(
//            表单提交的用户名
            String userName) throws UsernameNotFoundException {
//        1、从数据库中查询admin对象
        String sql = "SELECT id,loginAcct,userPswd,userName,email,createtime FROM t_admin WHERE loginacct = ?";
        List<Admin> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Admin>(Admin.class), userName);
        Admin admin = list.get(0);
        String userPswd = admin.getUserPswd();
//      给admin设置对象权限信息
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_学徒"));
        authorities.add(new SimpleGrantedAuthority("UPDATE"));


//        将admin对象和authorities封装到UserDetails中
        return new User(userName, userPswd, authorities);
    }
}
