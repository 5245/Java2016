package com.gewara.model.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gewara.model.BaseObject;
import com.gewara.util.DateUtil;

public class MemberUsefulAddress extends BaseObject {

	private static final long serialVersionUID = 2836157511625416677L;
	// 默认地址
	public static final String DEFAULT_ADDRESS = "Y";
	// 非默认地址
	public static final String NOT_DEFAULT_ADDRESS = "N";
	private Long id;
	private Long memberid;
	private String realname;
	private String address;
	private String postalcode;
	private Timestamp addtime;
	private String mobile;
	private String IDcard;
	
	private String provincecode;
	private String provincename;
	private String citycode;
	private String cityname;
	private String countycode;
	private String countyname;
	// 默认地址：Y是，N不是
	private String defaultAddress;
	
	public MemberUsefulAddress(){
		defaultAddress = NOT_DEFAULT_ADDRESS;
	}
	
	public MemberUsefulAddress(Long memberid, String realname, String address, String postalcode){
		this.memberid = memberid;
		this.realname = realname;
		this.address = address;
		this.postalcode = postalcode;
		this.defaultAddress = NOT_DEFAULT_ADDRESS;
		this.addtime = DateUtil.getCurFullTimestamp();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * memberid 有可能为空
	 */
	public Long getMemberid() {
		return memberid;
	}

	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public Timestamp getAddtime() {
		return addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIDcard() {
		return IDcard;
	}

	public void setIDcard(String iDcard) {
		IDcard = iDcard;
	}

	@Override
	public Serializable realId() {
		return id;
	}

	public String getProvincecode() {
		return provincecode;
	}

	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCountycode() {
		return countycode;
	}

	public void setCountycode(String countycode) {
		this.countycode = countycode;
	}

	public String getCountyname() {
		return countyname;
	}

	public void setCountyname(String countyname) {
		this.countyname = countyname;
	}

	public String getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
}
