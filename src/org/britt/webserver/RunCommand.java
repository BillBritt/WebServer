package org.britt.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunCommand {

    public static String exec(String command) {
        String s;
        StringBuilder sb = new StringBuilder();

        try {
            String[] cmd = {"/bin/sh", "-c", command + " 2>&1"};

            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = stdInput.readLine()) != null) {
                sb.append(s + "\n");
            }


        } catch (IOException e) {
        } finally {
            return sb.toString();
        }
    }
}