package org.contecale.common.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class PropertiesManager {
    protected File infoFile;
    protected Properties props;

    protected String propertiesTitle;

    public PropertiesManager(File infoFile, String propertiesTitle) {
        this.infoFile = infoFile;
        props = new Properties();
        this.propertiesTitle = propertiesTitle;
        if (infoFile.exists())
            loadData();
        else
            saveDefaultData();
    }

    public PropertiesManager(String infoFilePath, String propertiesTitle) {
        this(new File(infoFilePath), propertiesTitle);
    }

    private void loadData() {
        try {
            props.load(new FileInputStream(infoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            props.store(new FileOutputStream(infoFile), propertiesTitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void saveDefaultData();

    public String getPropery(String key) {
        return props.getProperty(key);
    }

    public void setProperty(String key, String newValue) {
        props.setProperty(key, newValue);
        saveData();
    }

    public void addProperty(String key, String value) {
        setProperty(key, value);
    }
}
