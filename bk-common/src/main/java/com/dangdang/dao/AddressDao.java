package com.dangdang.dao;

import java.util.List;

import com.dangdang.model.TAddress;

public interface AddressDao {
    /**
     * 增加地址
     * @param address
     * @return
     *
     */
    public String save(TAddress address);

    /**
     * 修改地址
     * @param address
     * @return
     *
     */
    public TAddress update(TAddress address);

    /**
     * 删除地址
     * @param address
     * @return
     *
     */
    public String delete(String id);

    /**
     * 查询地址
     * @param address
     * @return
     *
     */
    public TAddress findById(String id);

    /**
     * 查询地址
     * @param address
     * @return
     */
    public List<TAddress> findByUserId(String userId);

    /**
     * 统计地址个数 
     * @param userId
     * @return
     */
    public Integer countByUserId(String userId);

}
