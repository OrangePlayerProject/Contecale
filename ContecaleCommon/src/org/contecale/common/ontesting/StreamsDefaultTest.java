package org.contecale.common.ontesting;

import org.contecale.common.io.StreamsConfig;
import org.contecale.common.io.streams.ContecaleInputStream;
import org.contecale.common.io.streams.ContecaleOutputStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.contecale.common.io.StreamsConfig.SENDBUFFSIZE;

public class StreamsDefaultTest {
    public static void main(String[] args) throws IOException {
        int port = 4000;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket client = new Socket("localhost", port);
        Socket serverClient = serverSocket.accept();

        ContecaleOutputStream outputStream = new ContecaleOutputStream(
                client.getOutputStream());
        ContecaleInputStream inputStream = new ContecaleInputStream(
                serverClient.getInputStream());

        client.setSendBufferSize(SENDBUFFSIZE);
        serverClient.setReceiveBufferSize(StreamsConfig.RECVBUFFSIZE);

        StringBuilder sbStr = new StringBuilder();
        for (int i = 0; i < SENDBUFFSIZE / 2; i++)
            sbStr.append("aaaa");

        String str = sbStr.toString();
        System.out.println(str.getBytes().length);
        outputStream.writeString(str);
        String recv = inputStream.readString();
        System.out.println(recv.length());
        System.out.println(str.equals(recv));

        client.close();
        serverClient.close();
        serverSocket.close();

    }
}
