package ${bussPackage}.entity.${entityPackage};

import td.enterprise.dmp.common.dao.entity.BaseEntity;
#foreach($importClasses in $!{entityImportClasses})
import ${importClasses};
#end
import lombok.Data;
/**
 *
 * 功能：${codeName} ${className}Entity
 * 作者：code generator
 * 日期： ${currentDate} 
 * 版权所有：Copyright(C) 2015, Beijing TendCloud Science & Technology Co., Ltd.
 */

@Data
public class ${className} extends BaseEntity {
	
#foreach($po in $!{columnDatas})
#if(${po.dataName} != 'updateTime' && ${po.dataName} != 'createTime')
  private ${po.shortDataType} ${po.dataName};
#end
#end

}

