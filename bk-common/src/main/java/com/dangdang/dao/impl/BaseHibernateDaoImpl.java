package com.dangdang.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.dao.BaseHibernateDao;

//@Repository
public abstract class BaseHibernateDaoImpl implements BaseHibernateDao {

    protected String          CLASS_NAME;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Object save(Object o) {
        return this.getHibernateTemplate().save(o);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(Object o) {
        this.getHibernateTemplate().delete(o);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(Object o) {
        this.getHibernateTemplate().update(o);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Object findById(final String idValue) {
        return this.getHibernateTemplate().get(CLASS_NAME, new String(idValue));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List findAll() {
        return this.getHibernateTemplate().find("select acc from " + CLASS_NAME + " as acc");
    }

}
