package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.GwrProvinceDao;
import com.dangdang.model.GwrProvince;

@Repository
public class GwrProvinceImpl extends BaseHibernateDaoImpl implements GwrProvinceDao {

    public GwrProvinceImpl() {
        this.CLASS_NAME = GwrProvince.class.getName();
    }
}
