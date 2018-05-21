/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contecale.common.io.streams;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author martin
 */
public class ContecaleOutputStream extends DataOutputStream{

    //private final Encryptor encryptor;
    
    public ContecaleOutputStream(OutputStream out) {
        super(out);
        //this.encryptor = new Encryptor();
    }

    public void writeString(final String str) throws IOException {
        //final byte[] bytes = /*encryptor.encrypt(str)*/str.getBytes();
        writeBytes(str.getBytes());
        /*final char[] chars = encryptor.encrypt(str).toCharArray();
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++)
            bytes[i] = (byte)chars[i];
        super.write(bytes);*/
    }

    public void writeBytes(byte[] data) throws IOException {
        super.write(data, 0, data.length);
    }
    
}
