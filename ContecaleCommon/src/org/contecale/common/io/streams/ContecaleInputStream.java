/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contecale.common.io.streams;

import org.contecale.common.io.ByteBuffer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author martin
 */
public class ContecaleInputStream extends DataInputStream{

    //private final Encryptor encryptor;
    private final StreamBuffer streamBuffer;
    private static final short BUFF_SIZE = 1024;
    
    public ContecaleInputStream(InputStream in) {
        super(in);
        //this.encryptor = new Encryptor();
        streamBuffer = new StreamBuffer();
    }
    
    private void addBytes(byte[] array, StringBuilder sbStr){
        byte b;
        
        for(int i = 0; i < array.length && (b = array[i]) != 0; i++)
            sbStr.append((char)b);
    }
    
    private Byte[] getAvailableBytes(byte[] array){
        ArrayList<Byte> listBytes = new ArrayList<>();
        
        byte b;
        for(int i = 0; i < array.length && (b = array[i]) != 0; i++)
            listBytes.add(b);
        
        return (Byte[])listBytes.toArray();
    }

    private int getBuffSize(int bytesAvailable){
        if (bytesAvailable <= BUFF_SIZE)
            return bytesAvailable;
        else{
            int bigThat = bytesAvailable/BUFF_SIZE;
            return bigThat <= 10 ? bytesAvailable : BUFF_SIZE*10;
        }
    }
    
    private void waitForData() throws IOException{
        while(!hasData());
    }
    
    private void waitForData(long millis) throws IOException{
        final long ti = System.currentTimeMillis();
        while (!hasData())
            if (System.currentTimeMillis()-ti >= millis)
                break;
    }
    
    public boolean hasData() throws IOException{
        return available() > 0;
    }
    
    public String readString() throws IOException {
        waitForData();
        int bufferSize = getBuffSize(available());
        //System.out.println("BufferSize: "+bufferSize);
        byte[] bytes = new byte[bufferSize];

        // Calcular las vueltas del for de acuerdo a cuantas veces lee n bytes.
        while (hasData()){
            read(bytes);
            streamBuffer.addBytes(bytes);
            bytes = new byte[bufferSize];
        }
        //System.out.println("Cantidad de datos del buffer: "+streamBuffer.getBufferSize());
        String msg = streamBuffer.getAllBytes();
        //System.out.println("String del buffer: "+msg);
        //return encryptor.decrypt(msg);
        return msg;
    }



    public byte[] readBytes() throws IOException {
        waitForData();
        //System.out.println("BufferSize: "+bufferSize);
        ByteBuffer buffer = new ByteBuffer(4096);

        // Calcular las vueltas del for de acuerdo a cuantas veces lee n bytes.
        while (hasData())
            buffer.add(read());
        //System.out.println("Cantidad de datos del buffer: "+streamBuffer.getBufferSize());
        //System.out.println("String del buffer: "+msg);
        //return encryptor.decrypt(msg);
        return buffer.getAndClear();
    }

}
