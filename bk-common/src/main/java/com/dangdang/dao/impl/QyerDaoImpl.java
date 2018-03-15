package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.QyerDao;
import com.dangdang.model.Qyer;

@Repository
public class QyerDaoImpl extends BaseHibernateDaoImpl implements QyerDao {

    public QyerDaoImpl() {
        this.CLASS_NAME = Qyer.class.getName();
    }
}
