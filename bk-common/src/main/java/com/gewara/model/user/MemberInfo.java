package com.gewara.model.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

//import com.gewara.constant.MemberConstant;
import com.gewara.model.BaseObject;
import com.gewara.util.JsonUtils;

public class MemberInfo extends BaseObject {
	private static final long serialVersionUID = -3838425704891306595L;
	public static String[] disallowBindField = new String[]{//不允许绑定的字段
		"regfrom", "headpic", "addtime", "updatetime", "rights", "expvalue", 
		"otherinfo", "newtask", "pointvalue", "inviteid", "ip"
	};

	private Long id;
	private String nickname;	//与Member中相同
	private String sex; 		// 性别
	
	private String fromcity;
	private String realname;
	private String invitetype;	//注册类型，比如：抽奖邀请
	private String source;		//注册来源
	
	//系统信息
	private String regfrom;
	private String headpic; 	//头像
	private Timestamp addtime;
	private Timestamp updatetime;
	private String rights;
	private Integer expvalue;	//经验值
	private String newtask;		//新手任务
	private String otherinfo;	//其他信息
	private Integer pointvalue;	//积分总值
	private Integer version;	//版本
	private Long inviteid; 		//邀请人ID
	private String ip; 			//注册IP
    public static String[] getDisallowBindField() {
        return disallowBindField;
    }
    public static void setDisallowBindField(String[] disallowBindField) {
        MemberInfo.disallowBindField = disallowBindField;
    }
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
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getFromcity() {
        return fromcity;
    }
    public void setFromcity(String fromcity) {
        this.fromcity = fromcity;
    }
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getInvitetype() {
        return invitetype;
    }
    public void setInvitetype(String invitetype) {
        this.invitetype = invitetype;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getRegfrom() {
        return regfrom;
    }
    public void setRegfrom(String regfrom) {
        this.regfrom = regfrom;
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
    public Timestamp getUpdatetime() {
        return updatetime;
    }
    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }
    public String getRights() {
        return rights;
    }
    public void setRights(String rights) {
        this.rights = rights;
    }
    public Integer getExpvalue() {
        return expvalue;
    }
    public void setExpvalue(Integer expvalue) {
        this.expvalue = expvalue;
    }
    public String getNewtask() {
        return newtask;
    }
    public void setNewtask(String newtask) {
        this.newtask = newtask;
    }
    public String getOtherinfo() {
        return otherinfo;
    }
    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }
    public Integer getPointvalue() {
        return pointvalue;
    }
    public void setPointvalue(Integer pointvalue) {
        this.pointvalue = pointvalue;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public Long getInviteid() {
        return inviteid;
    }
    public void setInviteid(Long inviteid) {
        this.inviteid = inviteid;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
