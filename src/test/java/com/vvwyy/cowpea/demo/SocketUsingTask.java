package com.vvwyy.cowpea.demo;

import java.io.IOException;
import java.net.Socket;

public abstract class SocketUsingTask<T> implements CancellableTask<T> {
    private Socket socket;

    protected synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void cancel() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            // ...
        }
    }
}
