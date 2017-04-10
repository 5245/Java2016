package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.ProvinceDao;
import com.dangdang.model.Province;

@Repository
public class ProvinceDaoImpl extends BaseHibernateDaoImpl implements ProvinceDao {

    public ProvinceDaoImpl() {
        this.CLASS_NAME = Province.class.getName();
    }
}
