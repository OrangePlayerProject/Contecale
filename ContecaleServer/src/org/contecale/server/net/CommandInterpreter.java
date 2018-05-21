package org.contecale.server.net;

import org.contecale.common.cmd.Command;

import java.io.IOException;

public interface CommandInterpreter {
    public void interprate(Command cmd) throws IOException;
}
