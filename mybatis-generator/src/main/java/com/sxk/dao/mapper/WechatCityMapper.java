package com.sxk.dao.mapper;

import com.sxk.model.WechatCity;

public interface WechatCityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatCity record);

    int insertSelective(WechatCity record);

    WechatCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatCity record);

    int updateByPrimaryKey(WechatCity record);
}