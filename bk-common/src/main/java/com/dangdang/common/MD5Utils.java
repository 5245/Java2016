package com.dangdang.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    // MD5密匙
    private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 获得一个字符窜经过MD5加密后的字符串.
     * @param s要加密的字符串
     * @return 经过MD5加密后的字符窜
     * @throws NoSuchAlgorithmException 
     */
    public final static String getMD5(String s) throws NoSuchAlgorithmException {
        if (null == s || 0 >= s.length()) {
            return null;
        }
        byte[] strTemp = s.getBytes();
        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
        mdTemp.update(strTemp);
        byte[] md = mdTemp.digest();
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }

        return (new String(str));
    }

    public static String fileMD5(File file) throws IOException {

        // 缓冲区大小（这个可以抽出一个参数）  
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;

        try {
            // 拿到一个MD5转换器（同样，这里可以换成SHA1）  
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用DigestInputStream  
            fileInputStream = new FileInputStream(file);
            
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            // read的过程中进行MD5处理，直到读完文件  
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0)
                ;
            // 获取最终的MessageDigest  
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素  
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串  
            return byteArrayToHex(resultByteArray);

        } catch (NoSuchAlgorithmException e) {
            return null;
        } finally {
            try {
                digestInputStream.close();
            } catch (Exception e) {
            }
            try {
                fileInputStream.close();
            } catch (Exception e) {

            }
        }

    }

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符  
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））  
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去  
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回  
        return new String(resultCharArray);
    }

    public static void main(String args[]) throws Exception {

        System.out.println(getMD5("jpg_20150316_001"));
        System.out.println(fileMD5(new File("D:/0.jpg")));
    }
}
