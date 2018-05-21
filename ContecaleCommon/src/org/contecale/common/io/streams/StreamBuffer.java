/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contecale.common.io.streams;

import org.contecale.common.io.BufferElement;

import java.util.ArrayList;

/**
 *
 * @author martin
 */
public class StreamBuffer {
    private final ArrayList<BufferElement> bufferBytes;
    
    public StreamBuffer() {
        bufferBytes = new ArrayList<>();
    }
    
    private int getElementsSize(){
        int elemSize = 0;

        for (int i = 0; i < getBufferSize(); i++)
            elemSize+=bufferBytes.get(i).getArraySize();
        return elemSize;
    }

    public int getBufferSize(){
        return bufferBytes.size();
    }

    public ArrayList<BufferElement> getBufferBytes() {
        return bufferBytes;
    }
    
    public void addBytes(byte[] bytes){
        bufferBytes.add(new BufferElement(bytes));
    }
    
    public String getAllBytes(){
        return getAllBytes(new StringBuilder());
    }
    
    public String getAllBytes(StringBuilder sbBytes){
//        final int bufSize = getBufferSize();
//        final int supBufSize = getElementsSize();
//        final byte[] supBuffer = new byte[supBufSize];
//        
//        BufferElement bufE;
//        int indexCounter = 0;
//        
//        for (int i = 0; i < bufSize; i++) {
//            bufE = bufferBytes.get(i);
//            for (int j = 0; j < bufE.getArraySize(); j++)
//                supBuffer[indexCounter++] = bufE.getByte(j);
//        }
//        bufferBytes.clear();
//        return new String(supBuffer);

        final int bufSize = getBufferSize();
        //final int supBufSize = getElementsSize();
        
        for (int i = 0; i < bufSize; i++)
            sbBytes.append(new String(bufferBytes.get(i).getArrayData()));
        String data = sbBytes.toString();
        
        bufferBytes.clear();
        return data;
    }

    /*public byte[] getBytes() {
        for (int i = 0; i < bufSize; i++) {
            buffer[i] = bufferBytes.get(i);
        }
            sbBytes.append(new String(bufferBytes.get(i).getArrayData()));
        String data = sbBytes.toString();

        bufferBytes.clear();
    }*/
//    
//    public static void main(String[] args) throws InvalidTextException {
////        StreamBuffer sb = new StreamBuffer();
////        String str = "hola";
////        byte[] b = {90, 90, 0};
////        
////        sb.addBytes(str.getBytes());
////        sb.addBytes(str.getBytes());
////        sb.addBytes(b);
//
//        String str = "texto";
//        Encryptor enc = new Encryptor();
//        StreamBuffer sb = new StreamBuffer();
//        String en = enc.encrypt(str);
//        sb.addBytes(en.getBytes());
//        String des = enc.decrypt(sb.getAllBytes());
//        System.out.println(des);
//    }
    
}
