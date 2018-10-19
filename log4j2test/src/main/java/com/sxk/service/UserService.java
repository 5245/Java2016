package com.sxk.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {
    public static boolean hello() {

        log.debug("我是debug信息");
        log.info("我是info信息");    //info级别的信息
        log.warn("我是warn信息");
        log.error("Did it again!");   //error级别的信息，参数就是你输出的信息

        return false;
    }

    public static void main(String[] args) {
        hello();
    }

}
