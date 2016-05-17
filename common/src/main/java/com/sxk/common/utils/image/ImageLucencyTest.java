package com.sxk.common.utils.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @description  透明图片
 * @author sxk
 * @email sxk5245@126.com
 * @date 2016年5月12日
 */
public class ImageLucencyTest {
    public static void main(String[] args) {
        ImageLucencyTest d = new ImageLucencyTest();
        d.createMark("D://a.jpg", "D://01.jpg", "", null, 1, "", 100);

    }

    public static boolean createMark(String filePath, String filePath1, String markContent, Color markContentColor, float qualNum, String fontType,
            int fontSize) {
        markContentColor = Color.gray;
        ImageIcon imgIcon = new ImageIcon(filePath);
        Image theImg = imgIcon.getImage();
        //Image可以获得 输入图片的信息 
        int width = theImg.getWidth(null);
        int height = theImg.getHeight(null);

        //800 800 为画出图片的大小 
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //2d 画笔 
        Graphics2D g = bimage.createGraphics();
        g.setColor(markContentColor);
        g.setBackground(Color.white);

        //画出图片----------------------------------- 
        g.drawImage(theImg, 0, 0, null);
        //画出图片----------------------------------- 

        //--------对要显示的文字进行处理-------------- 
        markContent = "中国";// 文字 
        AttributedString ats = new AttributedString(markContent);
        Font f = new Font(fontType, Font.BOLD, fontSize);
        ats.addAttribute(TextAttribute.FONT, f, 0, markContent.length());
        AttributedCharacterIterator iter = ats.getIterator();
        //---------------------- 

        g.drawString(iter, (int) (width - width + 10), (int) (height - height / 2));
        //添加水印的文字和设置水印文字出现的内容 ----位置 
        g.dispose();//画笔结束 

        try {
            //输出 文件 到指定的路径 
            FileOutputStream out = new FileOutputStream(filePath1);

            ImageIO.write(bimage, "jpg", out);

            //                    JPEGImageWriteParam param = new JPEGImageWriteParam(Locale.CHINA);
            //                   // param.setCompressionQuality(0.5f);
            //                    
            //                    ImageTranscoder encoder1 = ImageTranscoder.createImageEncoder("JPEG", out, param);
            //                    
            //                    JPEGImageWriteParam
            //                    
            //                    
            //                    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage); 
            //        
            //                    param.setQuality(qualNum, true); 
            //                    encoder.encode(bimage, param); 
            //                    out.close(); 
        } catch (Exception e) {
            return false;
        }

        return true;

    }
}
