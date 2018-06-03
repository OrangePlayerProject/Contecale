package org.contecale.common.io.streams;

import org.contecale.common.io.StreamsConfig;

public class StreamKey {
    public static final int BUFFSIZE = StreamsConfig.SENDBUFFSIZE;
    public static final byte MAJOR = 2;
    public static final byte HASMOREDATA = 3;
    public static final byte NOPKG = -3;
    public static final byte HASRES = -4;
    public static final byte NORES = -5;
    public static final byte EOF = -1;


}
