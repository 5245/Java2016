package com.sxk.web.vo.request;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description  用户注册 
 * @author sxk
 * @date 2016年7月7日
 *
 */
public class UserRegisterReqVO {
    @NotEmpty(message = "username is null")
    private String username;

    @NotEmpty(message = "password is null")
    private String password;

    @NotEmpty(message = "mobileNo is null")
    private String mobileNo;

    @NotEmpty(message = "email is null")
    private String email;

    private String photo;
    private int    status;

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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserRegisterReqVO() {
        super();
    }

    public UserRegisterReqVO(String username, String password, String mobileNo, String email, String photo, int status) {
        super();
        this.username = username;
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
        this.photo = photo;
        this.status = status;
    }

}
