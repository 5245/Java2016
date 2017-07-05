package com.gewara.model.user;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.gewara.model.BaseObject;

public class MemberMergerHis extends BaseObject{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long memberId;//合并账户ID
	private String memberPhone;//合并账户手机
	private Long mergerMemberId;//被合并账户ID
	private String mergerMemberNickName;//被合并账户的昵称
	private Long operationUserId;//操作人ID
	private Timestamp addtime;//插入时间
	private Timestamp updatetime;//更新时间
	private String otherInfo;//其他信息
	private String status;//状态：Y:绑定N：未绑定成功A：待审核
	private String remark;//备注
	private Long bindStep;//1：同步用户哇啦成功2：同步用户的优惠活动成功3：同步用户的优惠券成功4：同步曾经喜欢成功5：同步去过成功

	@Override
	public Serializable realId() {
		return id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public Long getMergerMemberId() {
		return mergerMemberId;
	}

	public void setMergerMemberId(Long mergerMemberId) {
		this.mergerMemberId = mergerMemberId;
	}

	public String getMergerMemberNickName() {
		return mergerMemberNickName;
	}

	public void setMergerMemberNickName(String mergerMemberNickName) {
		this.mergerMemberNickName = mergerMemberNickName;
	}

	public Long getOperationUserId() {
		return operationUserId;
	}

	public void setOperationUserId(Long operationUserId) {
		this.operationUserId = operationUserId;
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

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStatusName() {
		if(StringUtils.equals("Y", status)){
			return "已绑定";
		}else if(StringUtils.equals("N", status)){
			return "未绑定成功";
		}else if(StringUtils.equals("A", status)){
			return "待审核";
		}
		return "未知状态";
	}

	public Long getBindStep() {
		return bindStep;
	}

	public void setBindStep(Long bindStep) {
		this.bindStep = bindStep;
	}

}
