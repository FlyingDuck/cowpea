package com.vvwyy.cowpea.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket, InputStream in) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException e) {
            // handle
        } finally {
            super.interrupt();
        }
    }

    /*@Override
    public void run() {
        try {
            byte[] buf = new byte[BUFSZ];
            while (true) {
                int count = in.read(buf);
                if (count < 0) {
                    break;
                } else if (count > 0) {
                    processBuffer(buf, count);
                }

            }
        } catch (IOException e) {
            *//*允许线程退出*//*
        }
    }*/
}
