/**
 * 主键生成器.
 */
package com.dangdang.common;

import java.util.UUID;

/**
 * 主键生成器.
 * 
 * @author gm
 * 
 */
public abstract class IdGenerator {
    /**
     * 生成主键id.
     * 
     * @return uuid
     */
    public static String createId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}