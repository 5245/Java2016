package com.sxk.common.utils.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test {
    public static void main(String[] args) throws IOException {
        Test t = new Test();
        t.reduceImageByRatio("D://a.jpg", "D://test2016.jpg", 2, 2);

    }

    public void reduceImageByRatio(String srcImagePath, String toImagePath, int widthRatio, int heightRatio) throws IOException {
        FileOutputStream out = null;
        try {
            //读入文件  
            File file = new File(srcImagePath);
            // 构造Image对象  
            BufferedImage src = ImageIO.read(file);
            int width = src.getWidth();
            int height = src.getHeight();
            // 缩小边长 
            BufferedImage tag = new BufferedImage(width / widthRatio, height / heightRatio, BufferedImage.TYPE_INT_RGB);
            // 绘制 缩小  后的图片 
            tag.getGraphics().drawImage(src, 0, 0, width / widthRatio, height / heightRatio, null);
            out = new FileOutputStream(toImagePath);

            //保存新图片 
            ImageIO.write(tag, "jpg", out);
            //            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //            encoder.encode(tag);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
