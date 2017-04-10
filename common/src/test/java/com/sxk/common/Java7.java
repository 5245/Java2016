package com.sxk.common;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class Java7 {

    public static void main(String[] args) {
        Java7 java = new Java7();
        java.pathInfo();

        BlockingQueue<String> addTagQueue = new LinkedBlockingQueue<>();
        //TransferQueue 是BlockingQueue的改进版
        TransferQueue<String> smsQueue = new LinkedTransferQueue<>();

    }

    public void newTry() {

        try (FileOutputStream fos = new FileOutputStream("movies.txt"); DataOutputStream dos = new DataOutputStream(fos)) {
            dos.writeUTF("Java 7 Block Buster");
        } catch (IOException e) {
            // log the exception  
        }

    }

    public void pathInfo() {

        Path path = Paths.get("D:\\www");

        System.out.println("Number of Nodes:" + path.getNameCount());

        System.out.println("File Name:" + path.getFileName());

        System.out.println("File Root:" + path.getRoot());

        System.out.println("File Parent:" + path.getParent());

        System.out.println(path.toFile().toString());

        //这样写不会抛异常  
        //Files.deleteIfExists(path);
    }

    public void read(String filename) {
        FileInputStream input = null;
        IOException readException = null;
        try {
            input = new FileInputStream(filename);
        } catch (IOException ex) {
            readException = ex;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    if (readException == null) {
                        readException = ex;
                    } else {
                        //使用java7的
                        readException.addSuppressed(ex);
                    }
                }
            }
            if (readException != null) {
                readException.printStackTrace();
            }
        }
    }

}
