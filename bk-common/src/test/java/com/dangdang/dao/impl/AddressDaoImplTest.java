package com.dangdang.dao.impl;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dangdang.dao.AddressDao;
import com.dangdang.model.TAddress;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:application.xml" })
public class AddressDaoImplTest {

    @Autowired
    private AddressDao addressDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSave() {
        TAddress addr = new TAddress(UUID.randomUUID().toString().replace("-", ""));
        addr.setUserId("123456");
        addr.setConsignee("河马5");
        addr.setMobile("18712345678");
        String id = addressDao.save(addr);
        System.out.println(id);
    }

    @Test
    public void testFind() {
        TAddress address = addressDao.findById("0c086ff837484ce3ae6a85077c3d8f02");
        if (null != address) {
            System.out.println(address.toString());
            address.setCounty("中国");
            address.setProvince("北京");
            address.setCity("北京");
            TAddress a2 = addressDao.update(address);
            System.out.println(a2.toString());
        } else {
            System.out.println("...................no address");
        }
    }

    @Test
    public void testDelete() {
        String id = addressDao.delete("874379e5b6ed42d0aa2f96345908a860");
        System.out.println(id);
    }

    @Test
    public void testFindByUserId() {
        List<TAddress> addrs = addressDao.findByUserId("123456");
        System.out.println("........" + addrs.size());
        for (TAddress addr : addrs) {
            System.out.println(addr.toString());
        }
    }

    @Test
    public void testCountByUserId() {
        Integer total = addressDao.countByUserId("123456");
        System.out.println("address total: " + total);
    }

}
