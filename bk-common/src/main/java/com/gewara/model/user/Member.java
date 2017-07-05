/**
 * 
 */
package com.gewara.model.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//
//import com.gewara.constant.MemberConstant;
//import com.gewara.model.acl.GewaraUser;
import com.gewara.util.StringUtil;

/**
 * @author hxs(ncng_2006@hotmail.com)
 * @since Jan 27, 2010 10:18:24 AM
 */
public class Member {
	private static final long serialVersionUID = -5010141453720441090L;
	private Long id;
	private String nickname;
	private String email;
	private String password;
	private String mobile;
	private String rejected;
	private String bindStatus;	//N：未绑定，X：未知，特殊用途，Y:绑定，Y_S：手机能通话验证过
	private String prikey;		//密钥
	private String needValid;	//黄牛可能性：Y 需要短信回复验证，N：不需要验证，U：暂不需要验证
	private long lastLoginTime; //用户最后登录时间戳
	private String headpic; 	//头像
	private Timestamp addtime;
	private String ip; 			//注册IP
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getRejected() {
        return rejected;
    }
    public void setRejected(String rejected) {
        this.rejected = rejected;
    }
    public String getBindStatus() {
        return bindStatus;
    }
    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }
    public String getPrikey() {
        return prikey;
    }
    public void setPrikey(String prikey) {
        this.prikey = prikey;
    }
    public String getNeedValid() {
        return needValid;
    }
    public void setNeedValid(String needValid) {
        this.needValid = needValid;
    }
    public long getLastLoginTime() {
        return lastLoginTime;
    }
    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getHeadpic() {
        return headpic;
    }
    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }
    public Timestamp getAddtime() {
        return addtime;
    }
    public void setAddtime(Timestamp addtime) {
        this.addtime = addtime;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
  

	
	
	
}