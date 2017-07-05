package com.gewara.model.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gewara.model.BaseObject;
import com.gewara.util.DateUtil;
//验证账户的安全
public class MemberSecurityVerify extends BaseObject{
	private static final long serialVersionUID = -1486983903165253409L;
	private String securityCode;		//手机短信验证通过生成的随机加密的字符串
	private String deviceId;			//设备id
	private Long memberid;				//用户id
	private Timestamp addtime;			//创建时间
	public MemberSecurityVerify(){
		
	}
	public MemberSecurityVerify(String securityCode, String deviceId, Long memberid){
		this.securityCode = securityCode;
		this.deviceId = deviceId;
		this.memberid = memberid;
		this.addtime = DateUtil.getCurFullTimestamp();
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Long getMemberid() {
		return memberid;
	}
	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}
	public Timestamp getAddtime() {
		return addtime;
	}
	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}
	@Override
	public Serializable realId() {
		return securityCode;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	
}
