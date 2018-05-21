package org.contecale.common.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {
    public static byte[] getBytesOf(File file) throws IOException {
        if (!file.exists())
            return null;
        return Files.readAllBytes(file.toPath());
    }
}
