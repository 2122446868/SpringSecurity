package com.atguigu.security.entity;

/**
 * Admin
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/1/14 17:55]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Admin {
    private Integer id;
    private String loginAcct;
    private String userPswd;
    private String userName;
    private String email;

    public Admin() {
    }

    public Admin(Integer id, String loginAcct, String userPswd, String userName, String email) {
        this.id = id;
        this.loginAcct = loginAcct;
        this.userPswd = userPswd;
        this.userName = userName;
        this.email = email;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getUserPswd() {
        return userPswd;
    }

    public void setUserPswd(String userPswd) {
        this.userPswd = userPswd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", loginAcct='" + loginAcct + '\'' +
                ", userPswd='" + userPswd + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
