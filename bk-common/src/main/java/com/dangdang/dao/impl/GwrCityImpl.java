package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.GwrCityDao;
import com.dangdang.model.GwrCity;

@Repository
public class GwrCityImpl extends BaseHibernateDaoImpl implements GwrCityDao {

    public GwrCityImpl() {
        this.CLASS_NAME = GwrCity.class.getName();
    }
}
