package org.contecale.server.music;

import org.contecale.common.io.FileData;
import org.contecale.common.sys.ServerProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

public class MusicContainer {
    private ServerInfo info;
    private File musicFolder;
    private LinkedList<String> listSoundsPath;

    public MusicContainer() {
        info = new ServerInfo("infoServer.properties");
        musicFolder = new File(info.getPropery(ServerProperties.CONTAINERPATH));
    }

    // CRUD de musica

    public boolean addSound(FileData fileData) throws IOException {
        File newFile = new File(fileData.getParent(), fileData.getName());

        // Podria crearse un secundario para analizar los bytes aparte
        // y asi no dañar uno valido
        if (newFile.exists())
            newFile.createNewFile();
        if (!MusicAnalyzer.isValidSong(newFile)) {
            return false;
        }

        Files.write(newFile.toPath(), fileData.getData(), StandardOpenOption.WRITE);
        return true;
    }

    public boolean createFolder(String parent, String name) {
        File folder = new File(parent, name);
        if (folder.exists())
            return false;
        return folder.mkdir();
    }

    public boolean deleteFile(String path) {
        File file = new File(path);
        return file.exists() ? file.delete() : false;
    }



}
