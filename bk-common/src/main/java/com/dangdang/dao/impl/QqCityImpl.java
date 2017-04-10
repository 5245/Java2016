package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.QqCityDao;
import com.dangdang.model.QqCity;

@Repository
public class QqCityImpl extends BaseHibernateDaoImpl implements QqCityDao {

    public QqCityImpl() {
        this.CLASS_NAME = QqCity.class.getName();
    }
}
