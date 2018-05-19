package controllerPackage;

import org.json.JSONArray;
import org.json.JSONObject;
import uniandes.Interfaz.Apto;
import uniandes.Interfaz.InterfazPrincipal;
import uniandes.Interfaz.PanelBotones;

import javax.swing.plaf.ActionMapUIResource;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ActualizadorTiempoReal implements Runnable {

    private InterfazPrincipal interfaz;

    public ActualizadorTiempoReal(InterfazPrincipal interfaz) {
        this.interfaz = interfaz;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                URL url = new URL("http://localhost:8080/alarma/tiempoReal");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
//                DataOutputStream out = new DataOutputStream(con.getOutputStream());
//
//                String cuerpo = "{ \"email\": \"" + usuario + "\", \"password\": \"" + contrasena +"\"}";
//
//
//                out.writeBytes(cuerpo);
//                out.flush();
//                out.close();

                int status = con.getResponseCode();
//                tokenAutorizacion = con.getHeaderField("Authorization");

                //devuelvo el valor de
                System.out.println("Status is: " + status);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                JSONArray json = new JSONArray(content.toString());
                ArrayList<Apto> actualizar = new ArrayList<Apto>();
                for (Object jsonSon : json){
                    Apto aptoTmp = new Apto();
                    JSONObject jsonSonSon = (JSONObject) jsonSon;
                    aptoTmp.error = jsonSonSon.getString("mensaje");
                    aptoTmp.numero = jsonSonSon.getInt("inmueble");
                    actualizar.add(aptoTmp);
                }
                Apto aptoTmp = new Apto();
                aptoTmp.error = PanelBotones.APERTURA_NO_PERMITIDA;
                aptoTmp.numero = 10;
                actualizar.add(aptoTmp);

                con.disconnect();

                interfaz.Actualizar(actualizar);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
