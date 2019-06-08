package com.dunzung.cloud.uas.controller;

import com.dunzung.cloud.framework.mvc.rest.R;
import com.dunzung.cloud.framework.mvc.web.BaseController;
import com.dunzung.cloud.uas.entity.PwdRuleEntity;
import com.dunzung.cloud.uas.entity.vo.AccountVO;
import com.dunzung.cloud.uas.service.PwdRuleService;
import com.dunzung.cloud.uas.service.SsoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("用户校验操作")
@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountController extends BaseController {

    @Autowired
    private SsoService ssoService;

    @Autowired
    private PwdRuleService pwdRuleService;

    @ApiOperation(value = "查询密码策略详情", notes = "查询密码策略详情")
    @GetMapping(value = "/pwdrule")
    public R getPwdRule() {
        PwdRuleEntity pwdRule = pwdRuleService.findPwdRule();
        return R.ok().data(pwdRule);
    }

    @ApiOperation(value = "查询默认密码", notes = "查询默认密码")
    @GetMapping(value = "/password/default")
    public R getDefaultPassword() {
        return R.ok().data(pwdRuleService.findDefaultPassword());
    }

    @ApiOperation(value = "用户解锁", notes = "用户解锁")
    @ApiImplicitParam(name = "accountVO", value = "用户配置表单", required = true, dataType = "AccountVO")
    @PutMapping(value = "/unlock")
    public R unlock(@RequestBody AccountVO account) {
        Long[] userIds = account.getUserIds();
        logger.info("解锁用户ID{}", userIds);
        ssoService.unlock(userIds);
        return R.ok();
    }

    @ApiOperation(value = "密码重置", notes = "密码重置，用户名系统自动获取。")
    @ApiImplicitParam(name = "username", value = "用户名（系统获取）", required = true, dataType = "String")
    @PutMapping(value = "/password/reset")
    public R resetPassword() {
        //String username = AuthenticationHolder.getUsername();
        String userName = null;
        logger.info("{}重置密码", userName);
        ssoService.resetPassword(userName);
        return R.ok();
    }

    @ApiOperation(value = "密码修改", notes = "密码重置，用户名系统自动获取。")
    @ApiImplicitParam(name = "userLoginForm", value = "用户配置表单", required = true, dataType = "UserLoginForm")
    @PutMapping(value = "/password")
    public R updatePassword(@RequestBody AccountVO account) {
        //String username = AuthenticationHolder.getUsername();
        String userName = null;
        logger.info("{}密码修改", userName);
        ssoService.updatePassword(userName, account.getPassword());
        return R.ok();
    }

//    @ApiOperation(value = "密码校验", notes = "密码校验，用户名系统自动获取。")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "username", value = "用户名（系统获取）", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String")
//    })
//    @GetMapping(value = "/password/check/{password}")
//    public R check(@PathVariable("password") String password) {
//        String username = AuthenticationHolder.getUsername();
//        logger.info("{}密码校验", username);
//        ssoService.validatePassword(username, password);
//        return R.ok();
//    }
}
