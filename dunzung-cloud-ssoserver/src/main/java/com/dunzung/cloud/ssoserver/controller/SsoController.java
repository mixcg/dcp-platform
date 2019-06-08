package com.dunzung.cloud.ssoserver.controller;

import com.dunzung.cloud.framework.mvc.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api("SSO API接口")
@RestController
@RequestMapping("/api/v1/sso")
public class SsoController extends BaseController {

    @ApiOperation(value = "用户认证信息", notes = "用户认证信息")
    @GetMapping(value = "/authinfo")
    public Principal getUserAuthInfo(Principal principal) {
        logger.debug("用户认证信息如下:{}", principal.toString());
        return principal;
    }

}
