package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.CityDao;
import com.dangdang.model.City;

@Repository
public class CityDaoImpl extends BaseHibernateDaoImpl implements CityDao {

    public CityDaoImpl() {
        this.CLASS_NAME = City.class.getName();
    }
}
