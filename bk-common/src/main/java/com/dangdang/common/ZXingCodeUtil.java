package com.dangdang.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * zxing解析，生成二维码
 *
 */
public class ZXingCodeUtil {

    /**
     * 生成二维码
     * 
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public static void encode(String contents, int width, int height, String imgPath) {
        Map<EncodeHintType, Object> hints = new Hashtable();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析二维码
     * 
     * @param imgPath
     * @return
     */
    public static String decode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            /*Map<DecodeHintType, Object> hints = new Hashtable();
            hints.put(DecodeHintType.CHARACTER_SET, "GBK");*/

            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据流解析二维码
     * 
     * @param base64string
     * @return
     */
    public static String decodeStream(String base64string) {
        BufferedImage image = null;
        Result result = null;
        //使用BASE64对图片文件数据进行解码操作
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //通过Base64解密，将图片数据解密成字节数组
            byte[] bytes = decoder.decodeBuffer(base64string);

            //构造字节数组输入流
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            image = ImageIO.read(bais);

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成图像
     * 
     * @throws WriterException
     * @throws IOException
     */
    @Test
    public static void QREncode() throws WriterException, IOException {
        String fileName = "zxing.png";
        String filePath = ServletActionContext.getServletContext().getRealPath(fileName);
        JSONObject json = new JSONObject();
        json.put("zxing", "https://github.com/zxing/zxing/tree/zxing-3.2.0/javase/src/main/java/com/google/zxing");
        json.put("author", "shihy");
        String content = json.toJSONString();// 内容
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        System.out.println("输出成功.");
    }

    /**
     * 解析图像
     */
    @Test
    public static void QRDecode(String filePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            JSONObject content = JSONObject.parseObject(result.getText());
            System.out.println("图片中内容：	");
            System.out.println("author：	" + content.getString("author"));
            System.out.println("zxing：	" + content.getString("zxing"));
            System.out.println("图片中格式：	");
            System.out.println("encode：	" + result.getBarcodeFormat());
        } catch (IOException e) {
            System.out.println("二维码解析IOException");
            e.printStackTrace();
        } catch (NotFoundException e) {
            System.out.println("二维码解析NotFoundException");
            e.printStackTrace();
        }
    }

    /**
     * 条形码编码
     * 
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public static void BarEncode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height, null);

            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析条形码
     * 
     * @param imgPath
     * @return
     */
    public static String BarDecode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

       /* String appid = "wx68c16799a0f0f989";
        String callback = "http://ibangshou.cn/2017.php";
        String text = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" //
                + appid + "&redirect_uri=" + callback //
                + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";*/

        String text="http://totocun.com?rand"+System.currentTimeMillis();
        String imgPath = "D:\\003.jpg";
        // 益达无糖口香糖的条形码  
        String contents = "6923450657713";

        int width = 105, height = 50;
        ZXingCodeUtil handler = new ZXingCodeUtil();
        // handler.BarEncode(contents, width, height, imgPath);  
        //handler.QRDecode(imgPath);
        handler.encode(text, 500, 500, imgPath);
        System.out.println(handler.decode(imgPath));
        //System.out.println(handler.BarDecode(imgPath));

        System.out.println("Michael ,you have finished zxing EAN13 encode.");
    }
}
