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
        return Integer.parseInt(new String(buffer));
    }

    private byte[] readPackages(int packages) throws IOException {
        boolean hasResidue = (byte)read() == HASRES;
        int residue = 0;

        if (hasResidue)
            residue = readIntData();

        ByteBuffer buffer = new ByteBuffer(BUFFSIZE*packages+residue);

        for (int i = 0; i < packages*BUFFSIZE; i++)
            buffer.add(read());

        if (residue > 0)
            for (int i = 0; i < residue; i++)
                buffer.add(read());

        return buffer.getAndClear();
    }

    
    public String readString() throws IOException {
        return new String(readBytes());
    }

    public byte[] readBytes() throws IOException {
        int read = read();
        byte[] buff = null;
        if (read != EOF) {
            byte packages = (byte) read;
            if (packages == NOPKG) {
                int buffLen = readIntData();
                buff = new byte[buffLen];

                for (int i = 0; i < buffLen; i++)
                    buff[i] = (byte) read();
            }
            else {
                buff = readPackages(packages);
            }
        }
        return buff;
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
