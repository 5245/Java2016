package com.sxk.dao.mapper;

import com.sxk.model.City;

public interface CityMapper {
    int deleteByPrimaryKey(Long cityId);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Long cityId);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}