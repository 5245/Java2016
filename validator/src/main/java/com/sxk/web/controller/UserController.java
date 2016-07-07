package com.sxk.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sxk.web.msg.LogMsg;
import com.sxk.web.vo.request.UserLoginReqVO;
import com.sxk.web.vo.request.UserRegisterReqVO;
import com.sxk.web.vo.response.DataResult;
import com.sxk.web.vo.response.SingleResult;

/**
 * @description  UserController
 * @author sxk
 * @email sxk5245@126.com
 * @date 2016年5月26日
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public SingleResult login(@RequestBody @Validated UserLoginReqVO loginVO, BindingResult bindingResult) {
        logger.debug(LogMsg.WS_REQ_NO_REQ_ID, loginVO);
        this.valid(bindingResult);
        SingleResult result = new SingleResult("登录成功");
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DataResult<UserRegisterReqVO> register(@RequestBody @Validated UserRegisterReqVO registerVO, BindingResult bindingResult) {
        logger.debug(LogMsg.WS_REQ_NO_REQ_ID, registerVO);
        this.valid(bindingResult);
        DataResult<UserRegisterReqVO> result = new DataResult<UserRegisterReqVO>(registerVO);
        return result;
    }

    @RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
    public SingleResult getUserInfo(Integer uid) {
        logger.debug(LogMsg.WS_REQ_NO_REQ_ID, uid);
        SingleResult result = new SingleResult("用户信息");
        return result;
    }

}
