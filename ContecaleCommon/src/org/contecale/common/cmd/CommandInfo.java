package org.contecale.common.cmd;

import org.contecale.common.sys.PropertiesManager;

import java.io.File;

import static org.contecale.common.cmd.CommandKey.*;

public class CommandInfo extends PropertiesManager {

    public CommandInfo() {
        super(new File("cmdinfo.properties"), "Commands");
    }

    @Override
    protected void saveDefaultData() {
        addProperty(DELETE, "del");
        addProperty(LIST, "list");
        addProperty(VALIDATE, "valid");
        addProperty(UPLOAD, "fup");
    }

}
