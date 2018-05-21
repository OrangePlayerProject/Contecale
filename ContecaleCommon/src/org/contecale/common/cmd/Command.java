package org.contecale.common.cmd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Command {
    private String order;
    private List<String> listOptions;

    public Command() {
        listOptions = new ArrayList<>();
    }

    public Command(String strCmd) {
        String[] cmdSplit = strCmd.split(" ");
        listOptions = new ArrayList<>();
        if (cmdSplit.length <= 1)
            order = strCmd;
        else{
            order = cmdSplit[0];
            for (int i = 1; i < cmdSplit.length; i++)
                listOptions.add(cmdSplit[i]);
        }
    }

    public boolean hasOptions(){
        return !listOptions.isEmpty();
    }

    public int getOptionsCount(){
        return listOptions.size();
    }

    public void addOption(String option) {
        listOptions.add(option);
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOptionAt(int index){
        return hasOptions() ? listOptions.get(index) : null;
    }

    public List<String> getListOptions(){
        return listOptions;
    }

    public String toString(){
        if (!hasOptions())
            return order;
        StringBuilder sbCmd = new StringBuilder();
        sbCmd.append(order);
        for (int i = 0; i < listOptions.size(); i++)
            sbCmd.append(' ').append(listOptions.get(i));
        return sbCmd.toString();
    }

}
