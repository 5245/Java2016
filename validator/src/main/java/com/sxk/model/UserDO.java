package com.sxk.model;

/**
 * @description 用户实体类 
 * @author sxk
 * @date 2016年7月7日
 *
 */
public class UserDO extends BaseDO {

    private String username;
    private String password;
    private String mobileNo;
    private String email;
    private String photo;
    private int    status;
    private long   createDate;

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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public UserDO() {
        super();
    }

    public UserDO(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public UserDO(String username, String password, String mobileNo, String email, String photo, int status, long createDate) {
        super();
        this.username = username;
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
        this.photo = photo;
        this.status = status;
        this.createDate = createDate;
    }

}
