/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contecale.common.io.streams;

import org.contecale.common.io.ByteBuffer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import static org.contecale.common.io.streams.StreamKey.*;

/**
 *
 * @author martin
 */
public class ContecaleOutputStream extends OutputStream{

    private OutputStream socketStream;

    public ContecaleOutputStream(OutputStream socketStream) {
        this.socketStream = socketStream;
    }

    private String lenToString(int lenght) {
        return String.valueOf(lenght);
    }

    private byte[] lenghtToBytes(int lenght) {
        return String.valueOf(lenght).getBytes();
    }

    private void writeIntData(int data, ByteBuffer buffer) throws IOException {
        String lenStr = lenToString(data);
        buffer.add(HASRES);
        buffer.add(lenStr.length());
        buffer.addFrom(lenStr.getBytes());
        System.err.println("LenStr: "+lenStr);
        System.err.println("Len: "+lenStr.length());

    }

    private void writeData(final byte[] data) throws IOException {
        int dataLen = data.length;
        System.err.println("WriteDataAvailable: "+dataLen);
        ByteBuffer buffer = new ByteBuffer();
        if (dataLen > BUFFSIZE) {
            final int packets = dataLen / BUFFSIZE;
            final int residue = dataLen % BUFFSIZE;

            buffer.add(packets);
            if (residue > 0)
                writeIntData(residue, buffer);
            else
                buffer.add(NORES);
            write(buffer.drain());

            ByteBuffer dataBuff = new ByteBuffer(data);


            for (int i = 0; i < packets; i++)
                write(dataBuff.read(i*BUFFSIZE, BUFFSIZE));

            if (residue > 0)
                write(dataBuff.read(packets*BUFFSIZE));
        }
        else {
            write(NOPKG);
            write(data);
        }

    }

    public void writeString(final String str) throws IOException {
        writeData(str.getBytes());
    }

    public void writeBytes(final byte[] data) throws IOException {
        writeData(data);
    }

    @Override
    public void write(byte[] b) throws IOException {
        socketStream.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        socketStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        socketStream.flush();
    }

    @Override
    public void close() throws IOException {
        socketStream.close();
    }

    @Override
    public void write(int b) throws IOException {
        socketStream.write(b);
    }

}
