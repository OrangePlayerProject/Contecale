/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contecale.common.io.streams;

import com.sun.org.apache.bcel.internal.generic.NOP;
import org.contecale.common.io.ByteBuffer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.contecale.common.io.streams.StreamKey.*;


/**
 *
 * @author martin
 */
public class ContecaleInputStream extends InputStream{

    private InputStream socketStream;

    public ContecaleInputStream(InputStream socketStream) {
        this.socketStream = socketStream;
    }
    
    /*private void waitForData() throws IOException{
        while(!hasData());
    }

    public boolean hasData() throws IOException{
        return available() > 0;
    }*/

    private int readIntData() throws IOException {
        byte lenInt = (byte) read();
        byte[] buffer = new byte[lenInt];
        for (int i = 0; i < lenInt; i++)
            buffer[i] = (byte) read();
        int lenToInt = Integer.parseInt(new String(buffer));
        System.out.println("LenToInt: "+lenInt);
        return lenInt;
    }

    private byte[] readData() throws IOException {
        int available = available();
        System.err.println("ReadDataAvailable: "+available);
        ByteBuffer buffer = new ByteBuffer(available);
        byte firstCode = (byte) read();
        System.out.println("FirstCode: "+firstCode);

        // Hay mas de un paquete
        if (firstCode > 0) {
            // despues cambiar por una revision de un numero residue mas grande
            boolean hasResidue = ((byte) read()) == HASRES;
            int residue = 0;
            if (hasResidue)
                residue = readIntData();

            int recvSize = firstCode*BUFFSIZE;
            for (int i = 0; i < recvSize; i++)
                buffer.add(read());
            if (residue > 0)
                for (int i = 0; i < residue; i++)
                    buffer.add(read());
        }
        else {
            for(;;) {
                try {
                    buffer.add(read());
                } catch (IOException e) {
                    break;
                }
            }
        }
        return buffer.drain();
    }
    
    public String readString() throws IOException {
        return new String(readData());
    }

    public byte[] readBytes() throws IOException {
        return readData();
    }

    @Override
    public int read() throws IOException {
        return socketStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return socketStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return socketStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return socketStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return socketStream.available();
    }

    @Override
    public void close() throws IOException {
        socketStream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        socketStream.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        socketStream.reset();
    }

    @Override
    public boolean markSupported() {
        return socketStream.markSupported();
    }

}
