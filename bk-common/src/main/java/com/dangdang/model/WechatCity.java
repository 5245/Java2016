package com.dangdang.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

// Generated 2016-12-23 16:36:07 by Hibernate Tools 4.3.1

/**
 * WechatCity generated by hbm2java
 */
public class WechatCity implements java.io.Serializable {

    private Integer id;
    private String  nationalCode;
    private int     cityType;
    private String  name;
    private String  parentCode;
    private String  zipCode;
    private int     provinceId;
    private int     cityId;
    private int     districtId;
    private String  districtIdOther;

    public WechatCity() {
    }

    public WechatCity(Integer id) {
        this.id = id;
    }

    public WechatCity(String nationalCode, int cityType, String name, int provinceId, int cityId, int districtId, String districtIdOther) {
        this.nationalCode = nationalCode;
        this.cityType = cityType;
        this.name = name;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.districtId = districtId;
        this.districtIdOther = districtIdOther;
    }

    public WechatCity(String nationalCode, int cityType, String name, String parentCode, String zipCode, int provinceId, int cityId, int districtId,
            String districtIdOther) {
        this.nationalCode = nationalCode;
        this.cityType = cityType;
        this.name = name;
        this.parentCode = parentCode;
        this.zipCode = zipCode;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.districtId = districtId;
        this.districtIdOther = districtIdOther;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNationalCode() {
        return this.nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public int getCityType() {
        return cityType;
    }

    public void setCityType(int cityType) {
        this.cityType = cityType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return this.cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDistrictId() {
        return this.districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictIdOther() {
        return districtIdOther;
    }

    public void setDistrictIdOther(String districtIdOther) {
        this.districtIdOther = districtIdOther;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
