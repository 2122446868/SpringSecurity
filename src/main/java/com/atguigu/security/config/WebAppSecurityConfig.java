package com.atguigu.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

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

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyUserDetailsService userDetailsService;

//    @Autowired
//    private  MyPasswordEncoder passwordEncoder;

    /***
     * 使用带盐值加密
     * @return
     */
    // 默认单例不会重复创建
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /***
     * 重写父类另一个configure方法
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth); 禁用默认规则
        //在内存中比较
//        auth.inMemoryAuthentication()//在内存中完成 账号密码的检查
//                //设置用户名密码
//                .withUser("tom").password("123123")zhuru
//                // 设置角色
//                .roles("ADMIN", "学徒")
//                .and()
//                //设置用户名密码
//                .withUser("jerry").password("123123")
//                //设置权限
//                .authorities("SAVE", "EDIT", "内门弟子");
        // 装配userDetailsService对象
        auth.userDetailsService(userDetailsService).passwordEncoder(getBCryptPasswordEncoder());


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
        JdbcTokenRepositoryImpl tokenRepositoy = new JdbcTokenRepositoryImpl();
        tokenRepositoy.setDataSource(dataSource);

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
                //设置匹配/level1/**的地址
                .antMatchers("/level1/**")
                // 要求具备“学徒角色” 才能访问
                .hasRole("学徒")
                //  //设置匹配/level2/**的地址
                .antMatchers("/level2/**")
                // 要求具备“内门底子权限” 才能访问
                .hasAuthority("内门弟子")
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
                .logoutSuccessUrl("/index.jsp") // 退出成功后前往的地址
                .and()
//                指定异常处理器
                .exceptionHandling()
//                访问被拒绝时前往的页面
                .accessDeniedPage("/to/no/auth/page.html")
                // 自定义访问被拒绝时前往的页面
                .accessDeniedHandler(new AccessDeniedHandler() {
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                        request.setAttribute("message","抱歉！您无法访问当前页面！！！！");
                        request.getRequestDispatcher("/WEB-INF/views/no_auth.jsp").forward(request,response);

                    }
                })
                .and()
                // 开启记住我
                .rememberMe()
                .tokenRepository(tokenRepositoy)
        ;


    }
}
