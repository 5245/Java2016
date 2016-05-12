package com.sxk.common.utils.image;

import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class ImageTest {
    /** 
     * 先缩放，后居中切割图片 
     * @param srcPath 源图路径 
     * @param desPath 目标图保存路径 
     * @param rectw 待切割在宽度 
     * @param recth 待切割在高度 
     * @throws IM4JavaException  
     * @throws InterruptedException  
     * @throws IOException  
     */
    public static void cropImageCenter(String srcPath, String desPath, int rectw, int recth) throws IOException, InterruptedException, IM4JavaException {
        IMOperation op = new IMOperation();

        op.addImage();
        op.resize(rectw, recth, '^').gravity("center").extent(rectw, recth);
        op.addImage();

        ConvertCmd convert = new ConvertCmd(true);
  //      convert.createScript("D:\\myscript.sh",op);  生成执行脚本
  //      convert.setSearchPath("D:\\ProgramFiles\\GraphicsMagick-1.3.21-Q8");  设置环境变量后这个可以去掉   GRAPHICS_MAGICK_PATH D:\ProgramFiles\GraphicsMagick-1.3.21-Q8
        convert.run(op, srcPath, desPath);

    }

    public static void main(String[] agrs) {
        try {
            ImageTest.cropImageCenter("D:\\a_watermark.jpg", "D:\\01.jpg", 800, 200);
            System.out.println("剪切成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IM4JavaException e) {
            e.printStackTrace();
        }
    }
}
