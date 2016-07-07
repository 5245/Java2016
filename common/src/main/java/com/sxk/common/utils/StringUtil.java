package com.sxk.common.utils;

import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils {

    // a != b  ----->  a = a | b  , a 或者 b 只要有一个为 1, 那么，a 的最终结果就为 1  
    // a &= b  ----->  a = a & b  , a 和 b 二者必须都为 1, 那么，a 的最终结果才为 1
    // a ^= b  ----->  a = a ^ b  , 当且仅当 a 和 b 的值不一致时，a 的最终结果才为1，否则为0  
    public static boolean stringEquals(String a, String b) {
        int diff = a.length() ^ b.length();
        for (int i = 0; i < a.length() && i < b.length(); i++) {
            diff |= (int) a.charAt(i) ^ (int) b.charAt(i);
        }
        return diff == 0;
    }
}
