package ${dtoPackage}.dto.${entityPackage};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import td.enterprise.dmp.common.dao.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

#foreach($importClasses in $!{entityImportClasses})
import ${importClasses};
#end
import lombok.Data;
/**
 * 
 * 
 * 功能：${codeName} ${className}Response
 * 作者：code generator
 * 日期： ${currentDate} 
 * 版权所有：Copyright(C) 2015, Beijing TendCloud Science & Technology Co., Ltd.
 */
@ApiModel(description = "输出DTO")
@Data
public class ${className}Response {

#foreach($po in $!{columnDatas})
#if(${po.dataName} != 'updateTime' && ${po.dataName} != 'createTime'
&& ${po.dataName} != 'creator' && ${po.dataName} != 'createBy' && ${po.dataName} != 'updateBy')
  @ApiModelProperty(value = "")
  private ${po.shortDataType} ${po.dataName};
#end
#end

}
