package com.dangdang.model;

// Generated 2017-5-22 15:41:07 by Hibernate Tools 4.3.1

/**
 * GwrProvince generated by hbm2java
 */
public class GwrProvince extends BaseDO {

    private Integer id;
    private String  provincecode;
    private String  provincename;
    private String  provinceename;
    private String  nationcode;
    private String  nationname;
    private String  nationename;
    private String  continentcode;
    private String  continentname;
    private String  continentename;
    private String  regionlevel;

    public GwrProvince() {
    }

    public GwrProvince(String provincecode) {
        this.provincecode = provincecode;
    }

    public GwrProvince(String provincecode, String provincename, String provinceename, String nationcode, String nationname, String nationename,
            String continentcode, String continentname, String continentename, String regionlevel) {
        this.provincecode = provincecode;
        this.provincename = provincename;
        this.provinceename = provinceename;
        this.nationcode = nationcode;
        this.nationname = nationname;
        this.nationename = nationename;
        this.continentcode = continentcode;
        this.continentname = continentname;
        this.continentename = continentename;
        this.regionlevel = regionlevel;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvincecode() {
        return this.provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }

    public String getProvincename() {
        return this.provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public String getProvinceename() {
        return this.provinceename;
    }

    public void setProvinceename(String provinceename) {
        this.provinceename = provinceename;
    }

    public String getNationcode() {
        return this.nationcode;
    }

    public void setNationcode(String nationcode) {
        this.nationcode = nationcode;
    }

    public String getNationname() {
        return this.nationname;
    }

    public void setNationname(String nationname) {
        this.nationname = nationname;
    }

    public String getNationename() {
        return this.nationename;
    }

    public void setNationename(String nationename) {
        this.nationename = nationename;
    }

    public String getContinentcode() {
        return this.continentcode;
    }

    public void setContinentcode(String continentcode) {
        this.continentcode = continentcode;
    }

    public String getContinentname() {
        return this.continentname;
    }

    public void setContinentname(String continentname) {
        this.continentname = continentname;
    }

    public String getContinentename() {
        return this.continentename;
    }

    public void setContinentename(String continentename) {
        this.continentename = continentename;
    }

    public String getRegionlevel() {
        return this.regionlevel;
    }

    public void setRegionlevel(String regionlevel) {
        this.regionlevel = regionlevel;
    }

}
