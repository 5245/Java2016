package com.sxk.nio.ch1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;

public class ByteTest {
    public static void main(String[] args) throws IOException {
        ByteTest bt = new ByteTest();
        //FileUtils.copyFile(new File("D:/4wusuo.wmpc"), new File("D:/444.mp4"));
        //bt.copyFile();
        //bt.mapBuffer();
        ByteBuffer bf = ByteBuffer.allocate(15);
        System.out.println(bf.position() + ":limit:" + bf.limit() + ":capacity:" + bf.capacity());
        for (int i = 0; i < 10; i++) {
            //bf.put(i, (byte) i);
            bf.put((byte) i);
        }
        System.out.println(bf.position() + ":limit:" + bf.limit() + ":capacity:" + bf.capacity());
        bf.flip();
        System.out.println(bf.position() + ":limit:" + bf.limit() + ":capacity:" + bf.capacity());
        byte b;
        for (int i = 0; i < 5; i++) {
            b = bf.get();
            //System.out.println(b);
        }
        System.out.println(bf.position() + ":limit:" + bf.limit() + ":capacity:" + bf.capacity());
        bf.flip();
        System.out.println(bf.position() + ":limit:" + bf.limit() + ":capacity:" + bf.capacity());

    }

    public void copyFile() throws IOException {
        FileInputStream is = new FileInputStream("D:/a.txt");
        FileOutputStream os = new FileOutputStream("D:/b.txt");
        FileChannel ifc = is.getChannel();
        FileChannel ofc = os.getChannel();
        ByteBuffer bf = ByteBuffer.allocate(1024);
        while (true) {
            bf.clear();
            int length = ifc.read(bf);
            if (length == -1) {
                System.out.println("over");
                break;
            }
            //bf.put(100, (byte) 2);
            bf.flip();
            ofc.write(bf);
        }
        is.close();
        os.close();
        //FileUtils.copyFile(srcFile, destFile);
        //Files.copy(source, out);

    }

    public void mapBuffer() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("D:/b.txt", "rw");
        FileChannel fc = raf.getChannel();
        //将文件映射到内存
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, raf.length());
        while (mbb.hasRemaining()) {
            System.out.println((char) mbb.get());
        }
        mbb.put(0, (byte) 100);
        raf.close();
    }
}
