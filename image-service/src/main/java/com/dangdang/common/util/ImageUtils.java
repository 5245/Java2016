package com.dangdang.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * @author skp
 * @description 图片切割工具类 
 * @date 2017年1月23日
 *
 */
public class ImageUtils {
    
    /**
     * 读取图片基本信息
     * @param fileName
     * @return
     *
     */
    public static ImageInfo getImageInfo(String fileName) {
        FileInputStream inputStream = null;
        try {
            File image = new File(fileName);
            inputStream = new FileInputStream(image);

            BufferedImage sourceImg = ImageIO.read(inputStream);
            ImageInfo info = new ImageInfo();
            info.width = sourceImg.getWidth();
            info.height = sourceImg.getHeight();
            info.size = image.length();
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 切割高和宽
     * @param inFileName
     * @param outFileName
     * @param width
     * @param height
     * @param quality
     * @return
     *
     */
    public static boolean cropCenter(String inFileName, String outFileName, int width, int height, double quality) {
        ConvertCmd cmd = new ConvertCmd(true);
        IMOperation op = new IMOperation();
        op.addImage(new String[] { inFileName });
        op.resize();
        op.addRawArgs(new String[] { width + "x" + height + "^" });
        op.quality(Double.valueOf(quality));
        op.gravity("Center");
        op.crop(Integer.valueOf(width), Integer.valueOf(height), Integer.valueOf(0), Integer.valueOf(0));
        op.addImage(new String[] { outFileName });
        try {
            cmd.run(op, new Object[0]);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 横向切割
     * @param inFileName
     * @param outFileName
     * @param wxh
     * @param quality
     * @return
     *
     */
    public static boolean resize(String inFileName, String outFileName, String wxh, double quality) {
        ConvertCmd cmd = new ConvertCmd(true);
        IMOperation op = new IMOperation();
        op.addImage(new String[] { inFileName });
        op.resize();
        op.addRawArgs(new String[] { wxh });
        op.quality(Double.valueOf(quality));
        op.addImage(new String[] { outFileName });
        try {
            cmd.run(op, new Object[0]);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class ImageInfo {
        public int  width;
        public int  height;
        public long size;

        public String toString() {
            return String.format("width:%d,height:%d,size:%d", new Object[] { Integer.valueOf(this.width), Integer.valueOf(this.height), Long.valueOf(this.size) });
        }
    }
}
