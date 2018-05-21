package org.contecale.common.io;

public class FileData {
    private String name;
    private String parent;
    private byte[] data;

    public FileData() {}

    public FileData(String name, String parent, byte[] data) {
        this.name = name;
        this.parent = parent;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
