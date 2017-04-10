package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.WechatCityDao;
import com.dangdang.model.WechatCity;

@Repository
public class WechatCityImpl extends BaseHibernateDaoImpl implements WechatCityDao {

    public WechatCityImpl() {
        this.CLASS_NAME = WechatCity.class.getName();
    }
}
