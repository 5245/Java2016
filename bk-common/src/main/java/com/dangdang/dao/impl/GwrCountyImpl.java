package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.GwrCountyDao;
import com.dangdang.model.GwrCounty;

@Repository
public class GwrCountyImpl extends BaseHibernateDaoImpl implements GwrCountyDao {

    public GwrCountyImpl() {
        this.CLASS_NAME = GwrCounty.class.getName();
    }
}
