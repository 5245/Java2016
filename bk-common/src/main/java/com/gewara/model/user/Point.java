package com.gewara.model.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gewara.model.BaseObject;

public class Point extends BaseObject {
	private static final long serialVersionUID = 6556756647820158115L;
	private Long id;
	private Long memberid;
	private String tag;
	private Long tagid;
	private Integer point;
	private String reason;
	private Long adminid;
	private Timestamp addtime;
	private String statflag;
	public Point(){}
	public Point(Long memberid){
		this.memberid = memberid;
		this.addtime = new Timestamp(System.currentTimeMillis());
	}
	public Point(Long memberid, String tag){
		this.addtime = new Timestamp(System.currentTimeMillis());
		this.memberid = memberid;
		this.tag = tag;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Long getTagid() {
		return tagid;
	}
	public void setTagid(Long tagid) {
		this.tagid = tagid;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getAdminid() {
		return adminid;
	}
	public void setAdminid(Long adminid) {
		this.adminid = adminid;
	}
	public Timestamp getAddtime() {
		return addtime;
	}
	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}
	@Override
	public Serializable realId() {
		return id;
	}
	public Long getMemberid() {
		return memberid;
	}
	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public String getStatflag() {
		return statflag;
	}

	public void setStatflag(String statflag) {
		this.statflag = statflag;
	}
}
