/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsumidorHeartbeats;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author da.ramirezv
 */
public class ThreadHeartbeat implements Runnable {

    @Override
    public void run() {

        while (true) {

            if (ConsumidorHeartbeat.TIEMPO > 3) {

                try {
                    String remitente = "hub";
                    String destinatario = "fernando";
                    String asunto = "CERRADURA FUERA DE LINEA";
                    String cuerpo = "CERRADURA FUERA DE LINEA";

                    URL url = new URL("http://localhost:9090/correo");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(con.getOutputStream());

                    JSONObject tomJsonObj = new JSONObject();
                    tomJsonObj.put("remitente", remitente);
                    tomJsonObj.put("destinatarios", new String[]{destinatario});
                    tomJsonObj.put("asunto", asunto);
                    tomJsonObj.put("cuerpo", cuerpo);
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                break;
            }
        }
    }
}
