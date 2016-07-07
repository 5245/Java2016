package com.sxk.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sxk.web.msg.ResponseInfoEnum;

/**
 * 全局异常处理
 */
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger  = LoggerFactory.getLogger(GlobalHandlerExceptionResolver.class);

    private static final int    errcode = ResponseInfoEnum.FAIL.getRet();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        JSONObject rs = new JSONObject();
        rs.put("errcode", errcode);
        if (ex instanceof BaseRestException) {
            logger.info(((BaseRestException) ex).getMsg());
            rs.put("msg", ((BaseRestException) ex).getMsg());
        } else if (ex instanceof JSONException) {
            logger.error(ex.getMessage(), ex);
            rs.put("msg", ResponseInfoEnum.E10002.getMsg());
        } else if (ex instanceof DataAccessException) {
            logger.error(ex.getMessage(), ex);
            rs.put("msg", ResponseInfoEnum.E50001.getMsg());
        } else if (ex instanceof NullPointerException) {
            logger.error(ex.getMessage(), ex);
            rs.put("msg", ResponseInfoEnum.E10000.getMsg());
        } else {
            logger.error(ex.getMessage(), ex);
            response.setStatus(500);
            rs.put("msg", ResponseInfoEnum.E10000.getMsg());
        }
        response.setStatus(HttpStatus.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().print(rs.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }

}
