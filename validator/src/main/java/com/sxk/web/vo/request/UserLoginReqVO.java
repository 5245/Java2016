package com.sxk.web.vo.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.sxk.web.vo.valid.AddGroup;

/**
 * @description 用户登录 
 * @author sxk
 * @date 2016年7月7日
 */
public class UserLoginReqVO {
    @NotEmpty(message = "username is null", groups = AddGroup.class)
    private String username;

    @NotEmpty(message = "password is null", groups = AddGroup.class)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLoginReqVO() {
        super();
    }

    public UserLoginReqVO(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

}
