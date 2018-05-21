/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contecale.common.io;

import java.util.Arrays;

/**
 *
 * @author martin
 */
public class BufferElement {
    private final byte[] arrayData;
    private final int arraySize;

    public BufferElement(byte[] arrayData) {
        int sizeCounter = 0;
        
        for (int i = 0; i < arrayData.length && arrayData[i] != 0; i++)
            sizeCounter++;
        this.arrayData = new byte[sizeCounter];
        this.arraySize = sizeCounter;
        System.arraycopy(arrayData, 0, this.arrayData, 0, arraySize);
    }

    public byte[] getArrayData() {
        return arrayData;
    }
    
    public byte getByte(int pos){
        return arrayData[pos];
    }

    public int getArraySize() {
        return arraySize;
    }

    @Override
    public String toString() {
        return new String(arrayData);
    }
    
}
