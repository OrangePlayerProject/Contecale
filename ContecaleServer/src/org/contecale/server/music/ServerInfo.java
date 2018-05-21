package org.contecale.server.music;

import org.contecale.common.sys.PropertiesManager;
import org.contecale.common.sys.ServerProperties;

import java.io.File;
import java.util.Properties;

public class ServerInfo extends PropertiesManager {
    private File infoFile;
    private Properties props;

    public ServerInfo(File infoFile) {
        super(infoFile, "ServerInfo");
    }

    public ServerInfo(String infoFilePath) {
        this(new File(infoFilePath));
    }

    @Override
    protected void saveDefaultData() {
        addProperty(ServerProperties.CONTAINERPATH,
                "/home/martin/AudioTesting/music");
        addProperty(ServerProperties.DEFAULTPORT,
                String.valueOf(3200));
    }

    // Propiedades

}
