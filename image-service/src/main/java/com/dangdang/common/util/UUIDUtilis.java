package com.dangdang.common.util;

import java.util.UUID;

/**
 * 
 * @author skp
 * @description 
 * @date 2017年1月23日
 *
 */
public class UUIDUtilis {
    //32位uuid
    public static String uuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //36位uuid
    public static String uuid36() {
        return UUID.randomUUID().toString();
    }

}
