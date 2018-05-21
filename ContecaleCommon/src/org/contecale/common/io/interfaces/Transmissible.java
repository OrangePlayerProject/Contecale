package org.contecale.common.io.interfaces;

public interface Transmissible {
    public void send(byte[] data);
    public void send(String str);
    public void send(int data);
}
