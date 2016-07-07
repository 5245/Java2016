package com.sxk.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;
import com.sxk.common.utils.StringUtil;
import com.sxk.web.exception.BaseRestException;
import com.sxk.web.msg.ResponseInfoEnum;

/**
 * 
 * @description  baseController
 * @author sxk
 * @email sxk5245@126.com
 * @date 2016年5月26日
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private HttpServletRequest  request;
    private HttpServletResponse response;
    private HttpSession         session;

    @ModelAttribute
    protected void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected void addErrorMsg(JSONObject result, String msg) {
        result.put("errcode", ResponseInfoEnum.FAIL.getRet());
        result.put("msg", msg);
    }

    protected void addCorrectData(JSONObject result, Object obj) {
        result.put("errcode", ResponseInfoEnum.CORRECT.getRet());
        result.put("data", obj);
    }

    protected HttpServletRequest getRequest() {
        return request;
    }

    protected HttpServletResponse getResponse() {
        return response;
    }

    protected HttpSession getSession() {
        return session;
    }

    protected String getHeaderString(String name) {
        String value = "";
        if (StringUtil.isNotEmpty(name)) {
            value = request.getHeader(name);
        }
        return value;
    }

    /**
     * 校验
     * @param bindingResult
     */
    protected void valid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errs = bindingResult.getFieldErrors();
            StringBuffer sb = new StringBuffer();
            for (FieldError err : errs) {
                sb.append(",");
                sb.append(err.getDefaultMessage());
            }
            String msg = sb.toString();
            if (StringUtil.isNotEmpty(msg)) {
                msg = msg.substring(1);
            }
            logger.debug("valid msg:{}", msg);
            throw new BaseRestException(msg);
        }
    }
}
