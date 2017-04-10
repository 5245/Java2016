package com.sxk.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @description 
 * @author sxk
 * @email sxk5245@126.com
 * @date 2017年3月23日
 */
public class Base64Utils extends Base64 {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static void main(String[] args) {
    }
}
