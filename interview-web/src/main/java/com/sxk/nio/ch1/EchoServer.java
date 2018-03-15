package com.sxk.nio.ch1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    public static void main(String[] args) {
        ExecutorService tp = Executors.newCachedThreadPool();

        ServerSocket echoServer = null;
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(8000);

        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                clientSocket = echoServer.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + " connect success");
                tp.execute(new HandleMsg(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class HandleMsg implements Runnable {
        private Socket         clientSocket;
        private BufferedReader is;
        private PrintWriter    os;

        public HandleMsg(Socket clientSocket) {
            super();
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(), true);
                //
                String inputLine = null;
                long begin = System.currentTimeMillis();
                while (null != (inputLine = is.readLine())) {
                    os.println(inputLine);
                }
                long end = System.currentTimeMillis();
                System.out.println("spend:" + (end - begin) + " ms");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != os) {
                    os.close();
                }
                if (null != clientSocket) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
