package org.britt.webserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().toString();
        if (uri.equals("/bill")) {
            sendTextResponse(httpExchange, listMethods());
        } else {
            String methodName = uri.replaceAll("^/bill/", "process");
            try {
                Method method = this.getClass().getDeclaredMethod(methodName);
                String text = (String) method.invoke(this);
                sendTextResponse(httpExchange, text);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    private void sendTextResponse(HttpExchange httpExchange, String text) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Content-type", "text/html");
        httpExchange.sendResponseHeaders(200, text.getBytes().length);

        OutputStream os = httpExchange.getResponseBody();
        os.write(text.getBytes());
        os.close();
    }

    private String listMethods() {
        StringBuilder sb = new StringBuilder();

        sb.append("<h1>Listing:</h1>");

        for (Method method : this.getClass().getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.matches("^process[A-Z][a-zA-Z]*")) {
                methodName = methodName.replaceAll("^process","");
                String displayMethodName = methodName.replaceAll("([A-Z])", " $1").trim();
                sb.append("<li><a href=\"/bill/" + methodName + "\">" + displayMethodName + "</a>");
            }
        }

        return sb.toString();
    }

    private String preformattedText(String text) {
        StringBuilder sb = new StringBuilder();

        sb.append("<pre>");
        sb.append(text);
        sb.append("</pre>");

        return sb.toString();
    }

    private String processShowRunningProcesses() {
        return preformattedText(RunCommand.exec("ps wwwaux | sort"));
    }

    private String processShowRunningProcessesWithNetworking() {
        return preformattedText(RunCommand.exec("netstat -anp"));
    }

    private String processShowUsersLoggedIn() {
        return preformattedText(RunCommand.exec("who"));
    }

    private String processShowAwsBackupLog() {
        return preformattedText(RunCommand.exec("ls -al /tmp/awsbackup.log; echo; cat /tmp/awsbackup.log"));
    }


}
