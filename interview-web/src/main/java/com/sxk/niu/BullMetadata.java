package com.sxk.niu;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @description 存储元数据信息
 * @author sxk
 * @date 2017年10月31日
 */
@Data
@Builder
public class BullMetadata implements Serializable {
    private static final long serialVersionUID = 7999794985681065005L;
    private String            fileName;

}
