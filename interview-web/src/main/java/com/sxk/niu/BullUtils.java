package com.sxk.niu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @description 
 * @author sxk
 * @date 2017年10月31日
 */
public class BullUtils {

    private static final String fileName = "D:/a_back/storage.bull";

    public static boolean saveImage(Bull bull) {
        if (null == bull) {
            return false;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(bull);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static BullImage getImage(String imageName) {
        if (StringUtils.isEmpty(imageName)) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Bull bull = (Bull) ois.readObject();
            Map<BullMetadata, BullImage> map = bull.getMap();
            BullMetadata metaData = BullMetadata.builder().fileName(imageName).build();
            return map.get(metaData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buff2Image(byte[] b, String target) {
        if (StringUtils.isEmpty(target)) {
            return null;
        }
        try (FileOutputStream fout = new FileOutputStream(target)) {
            fout.write(b);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static byte[] image2Bytes(String imgSrc) {
        if (StringUtils.isEmpty(imgSrc)) {
            return null;
        }
        try (FileInputStream fin = new FileInputStream(new File(imgSrc))) {
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String filePath = "D:/a_back/" + "%d.png";
        String fileName = null;

        Map<BullMetadata, BullImage> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            fileName = String.format(filePath, i);
            byte[] imageBytes = image2Bytes(fileName);
            if (null == imageBytes) {
                continue;
            }
            BullImage bullFile = new BullImage();
            BullMetadata metaData = BullMetadata.builder().fileName(fileName).build();
            bullFile.setImageBytes(imageBytes);
            map.put(metaData, bullFile);
        }
        Bull bull = Bull.builder().map(map).build();
        System.out.println("保存:" + BullUtils.saveImage(bull));

        for (int i = 100; i > 0; i--) {
            fileName = String.format(filePath, i);
            BullImage bf = BullUtils.getImage(fileName);
            if (null == bf) {
                continue;
            }
            System.out.println("获取:" + buff2Image(bf.getImageBytes(), fileName.replace(".png", ".jpg")));
        }

    }
}
