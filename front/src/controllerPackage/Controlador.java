/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import static jdk.nashorn.internal.objects.NativeString.substring;
import uniandes.Interfaz.AptoHistorial;

/**
 *
 * @author cm.alba10
 */
public class Controlador {

    public String tokenAutorizacion;

    public ArrayList<String> darApto(int apto) throws ProtocolException, IOException {

        URL url = new URL("http://localhost8080/inmueble" + apto);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", tokenAutorizacion);
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());

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

        //aqui proceso el content me llega un json y debo extraer el atributo que tiene el propietario nombrePropietario le hago return a esa cosa 
        //que es solo un string
        ArrayList<String> infoApto = new ArrayList<>();
        //obtengo el nombre de los propietarios siguendo el ejemplo "nombrePropietario": "fernando alonso"
        String id = substring(content.toString().indexOf("\"id\" : \"") + 1, content.toString().indexOf("\""));
        infoApto.add(id);

        String numeroInmueble = substring(content.toString().indexOf("\"numeroInmueble\" : \"") + 1, content.toString().indexOf("\""));
        infoApto.add(numeroInmueble);

        String unidadResidencial = substring(content.toString().indexOf("\"unidadResidencial \" : \"") + 1, content.toString().indexOf("\""));
        infoApto.add(unidadResidencial);

        String nombrePropietario = substring(content.toString().indexOf("\"nombrePropietario \" : \"") + 1, content.toString().indexOf("\""));
        infoApto.add(nombrePropietario);

        in.close();
        con.disconnect();
        return infoApto;
    }

    public ArrayList<AptoHistorial> darHistorial() throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:9090/correo");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", tokenAutorizacion);
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());

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

        //Me llega un arreglo de json que tiene el historial sobre las alarmas, cada json tiene los atributos 
        //private String mensaje; XXX
        //  private String prioridad;
        //  private String unidadResidencial; XXX
        // private String inmueble;
        // private String dispositivo;
        // private Date fecha; XXX
        String[] historialArregloBruto = content.toString().split("{");
        ArrayList<AptoHistorial> arraylistAptoHistorial = new ArrayList<>();

        for (String stringBrutoIndividual : historialArregloBruto) {
            AptoHistorial historialActual = new AptoHistorial();

            historialActual.numero = Integer.parseInt(stringBrutoIndividual.substring(stringBrutoIndividual.indexOf("\"unidadResidencial\" : \"") + 1, stringBrutoIndividual.indexOf("\"")));
            historialActual.error = stringBrutoIndividual.substring(stringBrutoIndividual.indexOf("\"mensaje\" : \"") + 1, stringBrutoIndividual.indexOf("\""));
            historialActual.fecha = stringBrutoIndividual.substring(stringBrutoIndividual.indexOf("\"fecha\" : \"") + 1, stringBrutoIndividual.indexOf("\""));

            arraylistAptoHistorial.add(historialActual);
        }

        in.close();
        con.disconnect();

        return arraylistAptoHistorial;

    }

    public String enviar(String usuario, String contrasena) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:9090/correo");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());

        out.flush();
        out.close();

        int status = con.getResponseCode();
        tokenAutorizacion = con.getHeaderField("Authorization");

        //devuelvo el valor de 
        System.out.println("Status is: " + status);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        return String.valueOf(status);
    }

    public void logout() {
        tokenAutorizacion = "...";
    }
}
