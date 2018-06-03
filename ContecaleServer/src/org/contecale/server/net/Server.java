package org.contecale.server.net;

import org.contecale.common.sys.ContecaleInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Server {
    private ServerSocket serverSocket;
    private LinkedList<TClient> listClients;

    private Server() throws IOException {
        this.serverSocket = new ServerSocket(ContecaleInfo.DEFAULTPORT);
        listClients = new LinkedList<>();
    }

    public void addClient(TClient client) {
        if (!client.isAlive())
            client.start();
        listClients.add(client);
    }

    public static void main(String[] args) {

    }


}
