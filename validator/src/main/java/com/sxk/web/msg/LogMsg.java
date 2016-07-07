package com.sxk.web.msg;

public class LogMsg {
    /** 基本消息头(携带RequestId),此常量应附加于log消息的前部 **/
    public static final String BASE_HEADER        = "\nrequestId: {}\n";

    /** 基本消息模板**/
    public static final String BASE_MSG           = "\nrequestId: {}\n{}";

    /** debug 消息 **/
    public static final String WS_REQ_NO_REQ_ID   = "HTTP Request Body:{}";
    public static final String WS_RES_NO_REQ_ID   = "HTTP Response Body:{}";
    public static final String WS_REQ             = BASE_HEADER + WS_REQ_NO_REQ_ID;
    public static final String WS_RES             = BASE_HEADER + WS_RES_NO_REQ_ID;

    /** info 消息 **/
    public static final String INSERT_DB          = "Insert to db: \n{}";
    public static final String UPDATE_DB          = "Update to db: \n{}";
    public static final String DELETE_DB          = "Delete to db: tableName:{}, id:{}";

    /** Warning 和 Error 消息 **/
    public static final String NO_REQ_ID          = "RequestId is missing!";

    public static final String ERR_STRING2INTEGER = "Wrong string can not parse int:{}";
    public static final String ERR_STRING2DATE    = "Wrong string can not parse date:{}";

    public static final String PERMISSION_DENIED  = "permission denied: \n{}";

}
