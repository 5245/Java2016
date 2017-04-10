package com.dangdang.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.dao.TencentQqAddressDao;
import com.dangdang.model.TencentQqAddress;

@Repository
public class TencentQqAddressImpl extends BaseHibernateDaoImpl implements TencentQqAddressDao {

    public TencentQqAddressImpl() {
        this.CLASS_NAME = TencentQqAddress.class.getName();
    }
}
