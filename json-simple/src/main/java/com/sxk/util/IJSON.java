package com.sxk.util;

public interface IJSON {

    String obj2Str(Object obj);

    <T> T str2Obj(String str, T t);

}
