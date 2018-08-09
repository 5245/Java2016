package ${bussPackage}.page.${entityPackage};

import ${baseClassPackage}.page.BasePage;

#foreach($importClasses in $!{entityImportClasses})
import ${importClasses};
#end

/**
 * 
 * <br>
 * <b>功能：</b>${codeName} ${className}Page<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> ${currentDate} <br>
 * <b>版权所有：<b>Copyright(C) 2015, Beijing TendCloud Science & Technology Co., Ltd.<br>
 */
public class ${className}Page extends BasePage {

#foreach($po in $!{columnDatas})
	private ${po.shortDataType} ${po.dataName};
#end
#foreach($po in $!{columnDatas})

	public ${po.shortDataType} get${po.upperDataName}() {
		return this.${po.dataName};
	}

	public void set${po.upperDataName}(${po.shortDataType} ${po.dataName}) {
		this.${po.dataName} = ${po.dataName};
	}
#end	
}
