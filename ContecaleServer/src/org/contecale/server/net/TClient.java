package org.contecale.server.net;

import org.contecale.common.cmd.Command;
import org.contecale.common.cmd.CommandInfo;
import org.contecale.common.cmd.CommandKey;
import org.contecale.common.io.FileData;
import org.contecale.common.io.streams.ContecaleInputStream;
import org.contecale.common.io.streams.ContecaleOutputStream;
import org.contecale.common.sys.ResponseType;
import org.contecale.server.music.MusicContainer;
import org.contecale.server.music.ServerInfo;

import java.io.IOException;
import java.net.Socket;

public class TClient extends Thread {
    private Socket cliSock;
    private ContecaleOutputStream outputStream;
    private ContecaleInputStream inputStream;

    private ServerInfo serverInfo;
    private MusicContainer container;
    private CommandInterpreter interpreter;
    private CommandInfo cmdInfo;

    private StringBuilder sbResponses;

    public TClient(Socket cliSock) throws IOException {
        this.cliSock = cliSock;
        outputStream = new ContecaleOutputStream(cliSock.getOutputStream());
        inputStream = new ContecaleInputStream(cliSock.getInputStream());
        container = new MusicContainer();
        cmdInfo = new CommandInfo();
        sbResponses = new StringBuilder();
        instanceInterpreter();
    }

    // lo que se hizo con filedata en los streams creados se hara en este hilo

    private void instanceInterpreter() {
        interpreter = cmd -> {
            final String order = cmd.getOrder();
            if (order.equals(cmdInfo.getPropery(CommandKey.DELETE))) {

            }
            else if (order.equals(cmdInfo.getPropery(CommandKey.LIST))) {

            }
            else if (order.equals(cmdInfo.getPropery(CommandKey.UPLOAD))) {
                // Ver si mas adelante se leen los datos del filedata como json
                FileData fileData = new FileData();
                fileData.setName(cmd.getOptionAt(0));
                fileData.setParent(cmd.getOptionAt(1));
                fileData.setData(inputStream.readBytes());
                boolean ok = container.addSound(fileData);
                if (ok)
                    buildResponse(ResponseType.OK, "Cancion subida correctamente");
                else
                    buildResponse(ResponseType.ERR, "Cancion invalida");
            }
            else if (order.equals(cmdInfo.getPropery(CommandKey.VALIDATE))) {

            }
            else {
                // Comando desconocido
                System.out.println("Comando desconocido");
            }
        };
    }

    private void waitForData() throws IOException {
        while (inputStream.available() == 0);
    }

    private void buildResponse(String responseType, String msg) {
        sbResponses.delete(0, sbResponses.length());
        sbResponses.append(responseType).append(msg);
    }

    public String getReceivData() throws IOException {
        return inputStream.readString();
    }

    public void sendResponse() throws IOException {
        outputStream.writeString(sbResponses.toString());
    }

    @Override
    public void run() {
        while (true) {
            try {
                interpreter.interprate(new Command(getReceivData()));
                sendResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
