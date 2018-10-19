package ${restPackage}.controller.${entityPackage};

import static java.util.stream.Collectors.toList;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import td.enterprise.dmp.common.util.BeanUtil;
import td.enterprise.dmp.common.web.BaseController;
import td.enterprise.dmp.dto.base.RetGrid;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.service.${entityPackage}.${className}Service;
import ${dtoPackage}.dto.${entityPackage}.${className}Response;
import ${dtoPackage}.dto.${entityPackage}.${className}Request;
import ${dtoPackage}.dto.${entityPackage}.${className}ListRequest;

/**
 * 
 */
@Slf4j
@RestController
@RequestMapping("/${urlPath}")
@Api(tags = {"${lowerName}"}, description = "${lowerName}")
public class ${className}Controller extends BaseController{

  @Autowired
  private ${className}Service ${lowerName}Service;

  @ApiOperation(value = "根据主键查询")
  @GetMapping(value="/{${lowerName}Id}")
  @ResponseBody
  public ${className} find(@PathVariable String ${lowerName}Id) throws Exception{
    ${className} data = ${lowerName}Service.selectByPrimaryKey(${lowerName}Id);
    return data;
  }
  @ApiOperation(value = "新增")
  @PostMapping()
  @ResponseBody
  public void create(@RequestBody ${className}Request ${lowerName}Req) throws Exception{
    ${className} ${lowerName}=new ${className}();
    BeanUtil.copyProperties(${lowerName},${lowerName}Req);
    ${lowerName}Service.insert(${lowerName});
  }
  @ApiOperation(value = "更新")
  @PutMapping(value="/{${lowerName}Id}")
  @ResponseBody
  public void update(@RequestBody ${className}Request ${lowerName}Req) throws Exception{
    ${className} ${lowerName}=new ${className}();
    BeanUtil.copyProperties(${lowerName},${lowerName}Req);
    ${lowerName}Service.updateByPrimaryKeySelective(${lowerName});
  }
  @ApiOperation(value = "根据主键删除")
  @DeleteMapping(value="/{${lowerName}Id}")
  @ResponseBody
  public void delete(@PathVariable String ${lowerName}Id) throws Exception{
    ${lowerName}Service.deleteByPrimaryKey(${lowerName}Id);
  }
  @ApiOperation(value = "查询列表")
  @PostMapping(value = "/list")
  @ResponseBody
  public RetGrid<${className}Response> query(@RequestBody ${className}ListRequest page) throws Exception {
    List<${className}> data = ${lowerName}Service.queryByList(page);
    List<${className}Response> rows = data.stream().map(
      ${lowerName} -> BeanUtil.copyPropertiesDest(new ${className}Response(), ${lowerName}))
        .collect(toList());
    return new RetGrid<>(page.getPager().getRowCount(), rows);
  }
}
