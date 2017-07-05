package com.sxk.util;

import com.alibaba.fastjson.JSON;

public class FastJUtils implements IJSON {

    @Override
    public String obj2Str(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T str2Obj(String str, T t) {
        
        return JSON.toJSON
    }
}
