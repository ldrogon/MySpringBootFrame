package com.sys.controller;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.base.condition.BaseCondition;
import com.base.condition.impl.NumberCondition;
import com.base.condition.impl.StringCondition;
import com.base.controller.BaseController;
import com.base.json.JsonMessage;
import com.base.util.I18nUtil;
import com.base.util.JsonUtil;
import com.sys.bean.MenuBean;
import com.sys.bean.RoleBean;
import com.sys.bean.UserBean;
import com.sys.service.RoleService;
import com.sys.util.ShiroUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Created by Administrator on 2017/5/10.
 */
@SuppressWarnings(value = "unchecked")
@RestController
@RequestMapping("/api/sys/role")
public class RoleController extends BaseController{
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value="查询所有角色列表",notes = "查询所有角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "角色id",dataType = "Long",paramType = "query"),
            @ApiImplicitParam(name = "name",value = "角色名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code",value = "角色编码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageNum",value = "当前页数(分页参数)",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示记录数(分页参数)",dataType = "int",paramType = "query")
    })
    @ApiResponses(value={@ApiResponse(code=200,message = "所有角色列表")})
    public JsonMessage<String> list(@RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "name",required = false) String name,
                                        @RequestParam(value = "code",required = false) String code,
                                        @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize){
        SimplePropertyPreFilter[] filters=JsonUtil.getOneDeepJsonFilter(RoleBean.class);
        BaseCondition condition= BaseCondition.and(
                new NumberCondition("id",id, NumberCondition.Handler.EQUAL),
                new StringCondition("name",name, StringCondition.Handler.ALL_LIKE),
                new StringCondition("code",code, StringCondition.Handler.ALL_LIKE)
        );
        if(pageNum==null||pageSize==null){
            return JsonMessage.successed(JsonUtil.toDefaultJSONString(roleService.findAll(condition),filters));
        }else{
            return JsonMessage.successed(JsonUtil.toDefaultJSONString(roleService.findAll(condition,new PageRequest(pageNum-1,pageSize)),filters));
        }
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ApiOperation(value = "保存角色",notes = "保存角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role",value = "角色实体",dataType = "SysRoleDTO",paramType = "body"),
    })
    @ApiResponses(value = {@ApiResponse(code = 200,message = "保存角色")})
    public JsonMessage<String> save(@RequestBody RoleBean role){
        roleService.save(role);
        return JsonMessage.successed(null,I18nUtil.getMessage("COMMON.SAVE_SUCCESSED"));

    }


    /**
     * 删除角色
     * @param roleIdArr
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除角色",notes = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleIdArr",value = "角色id数组",paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200,message = "删除角色")})
    public JsonMessage<String> delete(@RequestParam Long[] roleIdArr){
        roleService.deleteWithNoReferred(roleIdArr);
        return JsonMessage.successed(null,I18nUtil.getMessage("COMMON.DELETE_SUCCESSED"));
    }


    /**
     * 字段唯一性验证
     * @param fieldName
     * @param val
     * @return
     */
    @RequestMapping(value = "/isUniqueCheck",method = RequestMethod.GET)
    @ApiOperation(value = "字段唯一性验证",notes = "字段唯一性验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fieldName",value = "字段名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "fieldValue",value = "字段的值",dataType = "String",paramType = "query")
    })
    @ApiResponses(value = {@ApiResponse(code = 200,message = "true(可用) false(不可用)")})
    public JsonMessage<Object> isUniqueCheck(
            @RequestParam(value = "fieldName",required = true) String fieldName,
            @RequestParam(value = "fieldValue",required = true) String val){
        boolean flag = roleService.isUnique(fieldName, val);
        if (flag==false){
            return JsonMessage.failed(I18nUtil.getMessage("IsAvailable_FALSE"));
        }else {
            return JsonMessage.successed(null);
        }
    }

}
