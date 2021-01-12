package com.atguigu.security.config;

import org.springframework.context.annotation.Configuration;
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
@Configuration      //替代xml配置文件
@EnableWebSecurity // 表示启用web安全功能
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

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
                //对请求进行授权
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
                .failureForwardUrl("/to/login");


    }
}
