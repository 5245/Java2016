package com.sxk.web.msg;

/**
 * REST API返回Response的枚举
 *
 */
public enum ResponseInfoEnum {
    // 正确响应
    CORRECT(0, 0, 200, "success"), //
    FAIL(-1, -1, 200, "error"), //
    // 错误响应
    E10000(-1, -10000, 500, "Internal Server Error 服务器内部错误"), //
    E10001(-1, -10001, 405, "Method Not Allowed 不允许的操作, %s"), //
    E10002(-1, -10002, 400, "Request Params Not Valid 请求参数非法, %s"), //
    E10003(-1, -10003, 403, "Authentication Failed 权限校验错误"), //
    E10004(-1, -10004, 402, "Quota Use Up Payment Required 无quota"), //
    E10005(-1, -10005, 404, "Data Required Not Found 请求数据不存在"), //
    E10006(-1, -10006, 408, "Request Time Expires Timeout 请求已超时"), //
    E10007(-1, -10007, 408, "App Token Timeout appToken已经过期"), //
    E10008(-1, -10008, 409, "Duplicate Operation 重复操作, %s"), //
    E10009(-1, -10009, 404, "Url Required Not Found 请求的Url资源不存在"), //
    E10010(-1, -10010, 400, "Media Type Not Valid 请求的媒体类型(Content-Type)非法"), //

    E20001(-1, -20001, 411, "参数格式错误"), //

    E30000(-1, -30000, 500, "Permission Denied 没有权限,拒绝访问"), //

    E50001(-1, -50001, 500, "数据库异常"), //
    E50002(-1, -50002, 500, "Redis异常"), //
    E50003(-1, -50003, 503, "请求超时");//

    /**
     * 业务执行结果，正确响应则填写0，否则-1
     */
    private int    ret;
    /**
     * 业务的错误码，正确响应则填写0
     */
    private int    sub;
    /**
     * HTTP状态码，正确响应则填写200
     */
    private int    statusCode;
    /**
     * 业务的错误信息，正确响应则填写‘success’
     */
    private String msg;

    private ResponseInfoEnum(int ret, int sub, int statusCode, String msg) {
        this.ret = ret;
        this.sub = sub;
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getSub() {
        return sub;
    }

    public void setSub(int sub) {
        this.sub = sub;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\n");
        sb.append("\"ret\":").append(ret).append(",\n");
        sb.append("\"sub\":").append(sub).append(",\n");
        sb.append("\"msg\":").append("\"").append(msg).append("\"\n");
        sb.append("}");
        return sb.toString();
    }
}
