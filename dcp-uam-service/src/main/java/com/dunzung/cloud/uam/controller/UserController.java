package com.dunzung.cloud.uam.controller;

import com.dunzung.cloud.framework.mvc.pagination.PageEntity;
import com.dunzung.cloud.framework.mvc.pagination.PageParam;
import com.dunzung.cloud.framework.mvc.pagination.Pagination;
import com.dunzung.cloud.framework.mvc.rest.R;
import com.dunzung.cloud.framework.mvc.web.BaseController;
import com.dunzung.cloud.uam.entity.UserEntity;
import com.dunzung.cloud.uam.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Api("用户管理API文档")
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "用户姓名", dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户账号", dataType = "String"),
            @ApiImplicitParam(name = "userFlg", value = "用户启用/锁定状态", dataType = "Integer"),
            @ApiImplicitParam(name = "status", value = "生效标识", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/page")
    public R pageUser(@RequestBody Map<String, Object> pageMap) {
        String pageJson = gson.toJson(pageMap);
        PageParam param = gson.fromJson(pageJson, PageParam.class);
        UserEntity user = gson.fromJson(pageJson, UserEntity.class);
        PageEntity<UserEntity> page = new PageEntity<>(param.getPage(), param.getLimit());
        userService.pageUser(page, user);
        Pagination pagination = new Pagination(page.getList(), (int) page.getCount(), page.getPageSize(), page.getPageNo());
        return R.ok().data(pagination);
    }

    @ApiOperation(value = "获取用户详细", notes = "根据用户id获取用户详细信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    @GetMapping(value = "/{userId}")
    public R getUser(@PathVariable("userId") Long userId) {
        UserEntity user = userService.selectById(userId);
        return R.ok().data(user);
    }

    @ApiOperation(value = "添加用户", notes = "")
    @ApiImplicitParam(name = "user", value = "UserEntity", required = true, dataType = "UserEntity")
    @PostMapping
    public R saveUser(@RequestBody UserEntity user) {
        if (userService.checkUserName(user)) {
            return R.error("用户名已存在！");
        }
        user.setCreatedTm(new Date());
        user.setModifiedTm(new Date());
        userService.insert(user);
        return R.ok();
    }

    @ApiOperation(value = "修改用户", notes = "")
    @ApiImplicitParam(name = "user", value = "UserEntity", required = true, dataType = "UserEntity")
    @PutMapping
    public R updateUser(@RequestBody UserEntity user) {
        user.setModifiedTm(new Date());
        userService.updateById(user);
        return R.ok();
    }

    @ApiOperation(value = "删除用户", notes = "")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    @DeleteMapping(value = "/{userId}")
    public R deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteById(userId);
        return R.ok();
    }

}
