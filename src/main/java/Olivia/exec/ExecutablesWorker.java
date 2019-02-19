/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.exec;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author oscar.garcia
 */
public class ExecutablesWorker extends SwingWorker<List<String>, String>{
    //protected JTextArea ta;
    protected ExecutionOutputScreen ta;
    protected List<String> commands;
    protected int test=0,test2=0;

    public ExecutablesWorker(ExecutionOutputScreen ta, List<String> commands) {
        this.ta = ta;
        this.commands = commands;
    }

    public ExecutablesWorker(ExecutionOutputScreen ta, String... commands) {
        this(ta, Arrays.asList(commands));
    }

    @Override
    protected List<String> doInBackground() throws Exception {
        List<String> output = new ArrayList<>(25);
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);

        Process p;
        try{
            p= builder.start();
        }catch(IOException e){
            output.add("***\nERROR EXECUTING COMMAND:\n"+commands+"\n"+e+"\n***\n");
            publish("***\nERROR EXECUTING COMMAND:\n"+commands+"\n"+e+"\n***\n");
            return output;
        }
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line = null;
            while ((line = r.readLine()) != null) {
                //test2++;
                //setProgress(test2);
                output.add(line);
                publish(line);
            }
        }
            return output;
    }

    @Override
    protected void process(List<String> chunks) {
        //test++;
        for (String text : chunks) {
            //ta.append(text);
            //ta.append("\n");
            ta.addText(text + "\n");
            //ta.addText(test + " "+ test2 + "\n");
        }
    }
}
