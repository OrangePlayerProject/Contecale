package org.contecale.common.ontesting;

import org.contecale.common.cmd.Command;
import org.contecale.common.cmd.CommandInfo;
import org.contecale.common.cmd.CommandKey;
import org.contecale.common.io.ByteBuffer;
import org.contecale.common.io.FileData;
import org.contecale.common.io.FileManager;
import org.contecale.common.io.StreamsConfig;
import org.contecale.common.io.streams.ContecaleInputStream;
import org.contecale.common.io.streams.ContecaleOutputStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class StreamsTest {
    public static void main(String[] args) throws IOException {
        int port = 4000;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket client = new Socket("localhost", port);
        Socket serverClient = serverSocket.accept();

        ContecaleOutputStream outputStream = new ContecaleOutputStream(
                client.getOutputStream());
        ContecaleInputStream inputStream = new ContecaleInputStream(
                serverClient.getInputStream());

        client.setSendBufferSize(StreamsConfig.SENDBUFFSIZE);
        serverClient.setReceiveBufferSize(StreamsConfig.RECVBUFFSIZE);
        //System.out.println("ClientSendSize: "+client.getSendBufferSize());
        //System.out.println("ServerRecvSize: "+serverClient.getReceiveBufferSize());

        /*byte[] bytes = new byte[1024*1024];

        outputStream.writeBytes(bytes);
        System.out.println("Enviado");
        System.out.println(inputStream.readBytes().length);*/

        File file = new File("/home/martin/AudioTesting/audio/au.mp3");
        FileData fileData = new FileData();
        fileData.setName(file.getName());
        fileData.setParent(file.getParent());
        CommandInfo info = new CommandInfo();
        Command cmd = new Command();
        cmd.setOrder(info.getPropery(CommandKey.UPLOAD));
        cmd.addOption(file.getName());
        cmd.addOption(file.getParent());

        outputStream.writeString(cmd.toString());
        System.out.println("String enviado");
        byte[] bytes = FileManager.getBytesOf(file);
        outputStream.writeBytes(bytes);
        System.out.println("Pasamos outputStream.write");

        String strRecv = inputStream.readString();
        System.out.println("String recibido");
        cmd = new Command(strRecv);
        System.out.println("Datos");
        System.out.println("----------");
        System.out.println("Cmdd: "+cmd.getOrder());
        System.out.println("Name: "+cmd.getOptionAt(0));
        System.out.println("Parent: "+cmd.getOptionAt(1));

        System.out.println(inputStream.readBytes().length);
    }
}
