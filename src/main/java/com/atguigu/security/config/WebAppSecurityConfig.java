package com.atguigu.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * WebAppSecurityConfig
 * 配置类
 *
 * @author 赵长春
 * @version [版本号, 2021/1/12 17:16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Configuration      //将当前的类标记为配置类
@EnableWebSecurity // 表示启用web安全功能
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    /***
     * 重写父类另一个configure方法
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth); 禁用默认规则
        //在内存中比较
        auth.inMemoryAuthentication()
                //设置用户名密码
                .withUser("tom").password("123123")
                // 设置角色
                .roles("ADMIN")
                .and()
                //设置用户名密码
                .withUser("jerry").password("123123")
                //设置权限
                .authorities("SAVE", "EDIT");


    }

    /***
     * 重写configure
     * 修改默认规则
     * 默认规则是访问任何请求都必须要登录
     * @param security
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        //super.configure(security); 注释掉将取消父类方法中的默认规则
        security
                //对请求进行授权
                .authorizeRequests()
                //针对/index.jsp进行授权
                .antMatchers("/index.jsp")
                //可以无条件访问
                .permitAll()
                //针对/layui/**进行授权
                .antMatchers("/layui/**")
                //可以无条件访问
                .permitAll()
                .and()
//                //对请求进行授权
                .authorizeRequests()
                // 任意请求
                .anyRequest()
                // 需要登录后才可以正常访问
                .authenticated()
                .and()
                // 设置未授权请求跳转到登录页面 默认跳转到表单登录页
                .formLogin()
                //跳转到登录页面
                .loginPage("/index.jsp")
                // 指定提交登录表单的地址  loginProcessingUrl()方法指定了登录地址，就会覆盖 loginPage()方法中设置的默认值
                .loginProcessingUrl("/do/login.html")
                //定制登录账号的请求参数名  默认不设置是username
                .usernameParameter("loginAcct")
                //定制登录密码的请求参数名  默认不设置是password
                .passwordParameter("userPswd")
                //设置登录成功后默认跳转的登录地址
                .defaultSuccessUrl("/main.html")
                .and()
//                .csrf()
//                .disable()//禁用csrf功能
                .logout() //开启登录功能
                .logoutUrl("/do/logout.html")//指定处理推出请求的url地址
                .logoutSuccessUrl("/index.jsp"); // 退出成功后前往的地址


    }
}
