package com.gewara.model.pay;

import java.io.Serializable;
import java.sql.Timestamp;

//import com.gewara.constant.SmsConstant;
import com.gewara.model.BaseObject;

public class SMSRecord extends BaseObject {
	private static final long serialVersionUID = -3679476118020602182L;

	protected Long id;
	protected Long relatedid; // 关联场次，或者关联活动
	protected String tradeNo; // 订单号
	protected String contact; // 手机号码
	protected String content;
	protected Timestamp sendtime;	//计划发送时间
	protected Timestamp realtime;	//实际发送时间
	protected Timestamp validtime;
	protected String status;
	protected String smstype;
	protected Integer sendnum;
	protected String seqno;			//发送序列号
	protected String channel;			//发送渠道
	
	protected Long memberid;
	protected String tag;

	public SMSRecord() {}
	public SMSRecord(String contact) {
		this.sendnum = 0;
		this.status = "SmsConstant.STATUS_N";
		this.contact = contact;
	}
	public void copyFrom(SMSRecord another){
		this.relatedid = another.relatedid;
		this.tradeNo = another.tradeNo;
		this.contact = another.contact;
		this.content = another.content;
		this.sendtime = another.sendtime;
		this.validtime = another.validtime;
		this.smstype = another.smstype;
		this.sendnum = another.sendnum;
	}
	public SMSRecord(String tradeNo, String contact, String content, 
			Timestamp sendtime, Timestamp validtime, String smstype) {
		this(contact);
		this.tradeNo = tradeNo;
		this.contact = contact;
		this.content = content;
		this.sendtime = sendtime;
		this.validtime = validtime;
		this.smstype = smstype;
	}
	public SMSRecord(Long relatedid, String tradeNo, String contact, String content, 
			Timestamp sendtime, Timestamp validtime, String smstype) {
		this(tradeNo, contact, content, sendtime, validtime, smstype);
		this.relatedid = relatedid;
	}
	@Override
	public Serializable realId() {
		return id;
	}
	public String getSmstype() {
		return smstype;
	}

	public void setSmstype(String smstype) {
		this.smstype = smstype;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getSendtime() {
		return sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	public Timestamp getValidtime() {
		return validtime;
	}

	public void setValidtime(Timestamp validtime) {
		this.validtime = validtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Long getRelatedid() {
		return relatedid;
	}

	public void setRelatedid(Long relatedid) {
		this.relatedid = relatedid;
	}

	public Integer getSendnum() {
		return sendnum;
	}

	public void setSendnum(Integer sendnum) {
		this.sendnum = sendnum;
	}

	public void addSendnum() {
		this.sendnum++;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getMemberid() {
		return memberid;
	}

	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	public Timestamp getRealtime() {
		return realtime;
	}
	public void setRealtime(Timestamp realtime) {
		this.realtime = realtime;
	}
}
