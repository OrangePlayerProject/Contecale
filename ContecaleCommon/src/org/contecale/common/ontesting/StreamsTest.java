package org.contecale.common.ontesting;

import org.contecale.common.io.streams.ContecaleInputStream;
import org.contecale.common.io.streams.ContecaleOutputStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StreamsTest {
    public static void main(String[] args) throws IOException {
        int port = 4000;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket client = new Socket("localhost", port);
        Socket serverClient = serverSocket.accept();

        ContecaleInputStream inputStream =
                new ContecaleInputStream(serverClient.getInputStream());

        ContecaleOutputStream outputStream =
                new ContecaleOutputStream(client.getOutputStream());

        outputStream.writeBytes(new byte[]{1,2,3});
        System.out.println("Enviado");
        System.out.println(inputStream.readBytes().length);
        //File file = new File("/home/martin/AudioTesting/audio/au.mp3");
        //outputStream.writeFileData(file, file.getParent());
        //System.out.println("Pasamos outputStream.writeFile");
        /*Command cmd = new Command(inputStream.readString());
        System.out.println("Datos");
        System.out.println("----------");
        System.out.println("Name: "+cmd.getOrder());
        System.out.println("Parent: "+cmd.getOptionAt(0));
        System.out.println("Data: "+cmd.getOptionAt(1));
*/
    }
}
