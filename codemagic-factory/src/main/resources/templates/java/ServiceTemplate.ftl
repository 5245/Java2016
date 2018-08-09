package ${bussPackage}.service.${entityPackage};

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import td.enterprise.dmp.common.service.BaseService;
import ${bussPackage}.dao.${entityPackage}.${className}Dao;
import ${bussPackage}.entity.${entityPackage}.${className};

/**
 * 
 *
 * 功能：${codeName} ${className}Service
 * 作者：code generator
 * 日期： ${currentDate}
 * 版权所有：Copyright(C) 2015, Beijing TendCloud Science & Technology Co., Ltd.
 */
@Slf4j
@Service("$!{lowerName}Service")
public class ${className}Service extends BaseService<${className}> {

  @Autowired
  private ${className}Dao dao;

  public ${className}Dao getDao() {
    return dao;
  }
}
