package ${dtoPackage}.dto.${entityPackage};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
#foreach($importClasses in $!{entityImportClasses})
import ${importClasses};
#end
import lombok.Data;
/**
 *
 * 功能：${codeName} ${className}Request
 * 作者：code generator
 * 日期： ${currentDate} 
 * 版权所有：Copyright(C) 2015, Beijing TendCloud Science & Technology Co., Ltd.
 */
@ApiModel(description = "请求DTO")
@Data
public class ${className}Request {
	
#foreach($po in $!{columnDatas})
#if(${po.dataName} != 'updateTime' && ${po.dataName} != 'createTime')
  @ApiModelProperty(value = "")
  private ${po.shortDataType} ${po.dataName};
#end
#end

}

