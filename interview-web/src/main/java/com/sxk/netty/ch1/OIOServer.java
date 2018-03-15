package com.sxk.netty.ch1;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OIOServer {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(10101);
        System.out.println("server run...");
        while (true) {
            //阻塞
            final Socket socket = serverSocket.accept();
            System.out.println("come one client...");
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });

        }
    }

    private static void handler(Socket socket) {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] bytes = new byte[1024];
            while (true) {
                //阻塞
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                } else {
                    System.out.println(new String(bytes, 0, read));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
