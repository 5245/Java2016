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
 * 功能：${codeName} ${className}ListRequest
 * 作者：code generator
 * 日期： ${currentDate} 
 * 版权所有：Copyright(C) 2015, Beijing TendCloud Science & Technology Co., Ltd.
 */
@ApiModel(description = "分页DTO")
@Data
public class ${className}ListRequest extends BasePage {

#foreach($po in $!{columnDatas})
  @ApiModelProperty(value = "")
  private ${po.shortDataType} ${po.dataName};
#end

}
