package com.sxk.niu;

import java.io.Serializable;

import lombok.Data;

/**
 * @description 存储图片信息
 * @author sxk
 * @date 2017年10月31日
 */
@Data
public class BullImage implements Serializable {
    private static final long serialVersionUID = 570473128364594618L;
    private byte[]            imageBytes;

}
