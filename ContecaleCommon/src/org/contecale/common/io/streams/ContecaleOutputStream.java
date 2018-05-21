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

    private void writeIntData(int data) throws IOException {
        String strInt = lenToString(data);
        write(strInt.length());
        write(strInt.getBytes());

    }

    private void writeData(final byte[] data) throws IOException {
        int len = data.length;
        byte[] lenBytes = lenghtToBytes(len);
        if (len > BUFFSIZE) {
            int packets = len / BUFFSIZE;
            int residue = len % BUFFSIZE;
            boolean hasResidue = residue > 0;


            // Revisar que numeros estaran llegando
            write(packets);
            write(hasResidue ? HASRES : NORES);
            if (hasResidue)
                writeIntData(residue);

            ByteBuffer buffer = new ByteBuffer(data);
            byte[] sendBuffer = new byte[BUFFSIZE];

            for (int i = 0; i < packets; i++) {
                buffer.read(sendBuffer, i*BUFFSIZE, BUFFSIZE);
                write(sendBuffer);
            }
            if (residue > 0) {
                sendBuffer = new byte[residue];
                buffer.read(sendBuffer, packets*BUFFSIZE, residue);
                write(sendBuffer);
            }

        }
        else {
            write(NOPKG);
            writeIntData(len);
            write(data);
        }
    }

    public void writeString(final String str) throws IOException {
        //write('s');
        writeData(str.getBytes());
    }

    public void writeBytes(final byte[] data) throws IOException {
        //write('b');
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
