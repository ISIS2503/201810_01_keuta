/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvioHeartbeatHub;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 *
 * @author da.ramirezv
 */
public class ThreadHubHeartbeat implements Runnable {

    @Override
    public void run() {

        int s = 60;
        try {

            while (true) {

                URL url = new URL("http://localhost:9090/correo/heartbeat");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());

                JSONObject tomJsonObj = new JSONObject();
                tomJsonObj.put("timestamp", System.currentTimeMillis());
                tomJsonObj.put("idhub", "hub#12345");
                System.out.println(tomJsonObj);

                out.writeBytes(tomJsonObj.toString());
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("Status is: " + status);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println("Response is: " + content);
                in.close();
                con.disconnect();

                Thread.sleep(1000 * s);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
