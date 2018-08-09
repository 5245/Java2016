package ${bussPackage}.controller.${entityPackage};

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ${baseClassPackage}.web.BaseController;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.page.${entityPackage}.${className}Page;
import ${bussPackage}.service.${entityPackage}.${className}Service;
 
/**
 * 
 */ 
@Controller
@RequestMapping("/${entityPackage}") 
public class ${className}Controller extends BaseController{
	
	public final static Logger logger = Logger.getLogger(${className}Controller.class);
	
	@Autowired
	private ${className}Service ${lowerName}Service; 
		
	@RequestMapping(value = "/${lowerNames}", method = RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> query(${className}Page page) throws Exception {
		List<${className}> rows = ${lowerName}Service.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}
	
	@RequestMapping(value = "/${lowerNames}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ${className} create(@RequestBody ${className} ${lowerName}) throws Exception{
        ${lowerName}Service.insert(${lowerName});
        return ${lowerName};
    }
    
    @RequestMapping(value="/${lowerNames}/{${lowerName}Id}", method=RequestMethod.GET)
	@ResponseBody
	public ${className} find(@PathVariable String ${lowerName}Id) throws Exception{
        return ${lowerName}Service.selectByPrimaryKey(${lowerName}Id);
    }
    
    @RequestMapping(value="/${lowerNames}/{${lowerName}Id}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public ${className} update(@RequestBody ${className} ${lowerName}) throws Exception{
        ${lowerName}Service.updateByPrimaryKeySelective(${lowerName});
        return ${lowerName};
    }
    
    @RequestMapping(value="/${lowerNames}/{${lowerName}Id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<Object, Object> delete(@PathVariable String ${lowerName}Id) throws Exception{
        ${lowerName}Service.deleteByPrimaryKey(${lowerName}Id);
        return new HashMap<Object, Object>();
    }
}
