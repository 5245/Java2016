package ${bussPackage}.service.${entityPackage};

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ${baseClassPackage}.service.BaseService;
import ${bussPackage}.dao.${entityPackage}.${className}Dao;
import ${bussPackage}.entity.${entityPackage}.${className};

/**
 * 
 * <br>
 * <b>功能：</b>${codeName} ${className}Service<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> ${currentDate} <br>
 * <b>版权所有：<b>Copyright(C) 2015, Beijing TendCloud Science & Technology Co., Ltd.<br>
 */
@Service("$!{lowerName}Service")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ${className}Service extends BaseService<${className}> {
	public final static Logger logger = Logger.getLogger(${className}Service.class);
	
	@Autowired
	private ${className}Dao dao;

	public ${className}Dao getDao() {
		return dao;
	}
}
