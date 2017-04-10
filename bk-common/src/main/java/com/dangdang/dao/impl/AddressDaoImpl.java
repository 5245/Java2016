package com.dangdang.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.dao.AddressDao;
import com.dangdang.model.TAddress;

@Repository
public class AddressDaoImpl implements AddressDao {

    private static final String CLASS_NAME = TAddress.class.getName();

    @Autowired
    private HibernateTemplate   hibernateTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String save(TAddress address) {
        return (String) hibernateTemplate.save(address);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public TAddress update(TAddress address) {
        hibernateTemplate.update(address);
        return address;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String delete(String id) {
        String sql = "delete from " + CLASS_NAME + " where addrId=? ";
        int affectRows = hibernateTemplate.bulkUpdate(sql, id);
        System.out.println("affectRows: " + affectRows);
        return id;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public TAddress findById(String id) {
        return (TAddress) hibernateTemplate.get(CLASS_NAME, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<TAddress> findByUserId(String userId) {
        String sql = "select T from " + CLASS_NAME + " T where UserId=? and Status=?";
        return (List<TAddress>) hibernateTemplate.find(sql, userId, 1);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Integer countByUserId(String userId) {
        String sql = "select count(*) from " + CLASS_NAME + " where UserId=? ";
        List<Long> clist = (List<Long>) hibernateTemplate.find(sql, userId);
        return clist != null ? clist.get(0).intValue() : 0;
    }
}
