package org.contecale.server.music;


import java.io.File;

public class MusicAnalyzer {
    public static boolean isValidSong(File sound) {
        return Track.isValidTrack(sound.getPath());
    }

    public static boolean isValidSong(String path) {
        return Track.isValidTrack(path);
    }
}
