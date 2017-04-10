package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.DistrictDao;
import com.dangdang.model.District;

@Repository
public class DistrictDaoImpl extends BaseHibernateDaoImpl implements DistrictDao {

    public DistrictDaoImpl() {
        this.CLASS_NAME = District.class.getName();
    }
}
