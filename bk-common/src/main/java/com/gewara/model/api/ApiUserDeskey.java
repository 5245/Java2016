package com.gewara.model.api;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gewara.model.BaseObject;

public class ApiUserDeskey extends BaseObject{

	private static final long serialVersionUID = 8599456310113973388L;

	private String partnerkey;		//验证身份密码
	private String privatekey;		//私钥
	private String loginQry;		//联名登陆反查询	
	private Timestamp updatetime;	//更新时间
	private String otherinfo;		//json数据，其他信息
	
	@Override
	public Serializable realId() {
		return partnerkey;
	}
	public String getPartnerkey() {
		return partnerkey;
	}
	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}
	public String getOtherinfo() {
		return otherinfo;
	}
	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getPrivatekey() {
		return privatekey;
	}
	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}
	public String getLoginQry() {
		return loginQry;
	}
	public void setLoginQry(String loginQry) {
		this.loginQry = loginQry;
	}
}
