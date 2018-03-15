package com.sxk.niu;

import java.io.Serializable;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * @description 图片存储 
 * @author sxk
 * @date 2017年10月31日
 */
@Data
@Builder
public class Bull implements Serializable {
    private static final long            serialVersionUID = 4789256289195944279L;
    private Map<BullMetadata, BullImage> map;
}
