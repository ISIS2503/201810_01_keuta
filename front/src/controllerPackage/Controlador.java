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

import org.json.JSONArray;
import org.json.JSONObject;
import uniandes.Interfaz.AptoHistorial;
import uniandes.Interfaz.DetailedApto;
import uniandes.Interfaz.InterfazPrincipal;

/**
 *
 * @author cm.alba10
 */
public class Controlador {

    public String tokenAutorizacion;

    private InterfazPrincipal interfaz;

    private Thread t;

    public Controlador(InterfazPrincipal interfaz){
        this.interfaz = interfaz;
        t = new Thread(new ActualizadorTiempoReal(interfaz));
        t.start();
    }

    public DetailedApto darApto(int numero) throws ProtocolException, IOException {

        URL url = new URL("http://localhost:8080/inmueble/"+numero);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", tokenAutorizacion);
//        con.setDoOutput(true);
//        DataOutputStream out = new DataOutputStream(con.getOutputStream());

//        out.flush();
//        out.close();

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
//        ArrayList<AptoHistorial> propietarios = new ArrayList<>();
        //obtengo el nombre de los propietarios siguendo el ejemplo "nombrePropietario": "fernando alonso"
//        String propietariosBruto = substring(content.toString().indexOf("\"nombrePropietario\": \"") + 1, content.toString().indexOf("\""));
//        propietarios.add(propietariosBruto);

        JSONObject json = new JSONObject(content.toString());
        DetailedApto aptoTmp = new DetailedApto();
        aptoTmp.id = json.getString("id");
        aptoTmp.numeroInmueble = json.getInt("numeroInmueble");
        aptoTmp.unidadResidencial = json.getString("unidadResidencial");
        aptoTmp.nombrePropietario = json.getString("nombrePropietario");
//            propietarios.add(aptoTmp);


        in.close();
        con.disconnect();
//        return propietarios;
        return aptoTmp;
    }


    public ArrayList<AptoHistorial> darHistorial() throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/alarma");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", tokenAutorizacion);
//        con.setDoOutput(true);
//        DataOutputStream out = new DataOutputStream(con.getOutputStream());

//        out.flush();
//        out.close();

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

        JSONArray json = new JSONArray(content.toString());
        ArrayList<AptoHistorial> actualizar = new ArrayList<AptoHistorial>();
        for (Object jsonSon : json){
            AptoHistorial aptoTmp = new AptoHistorial();
            JSONObject jsonSonSon = (JSONObject) jsonSon;
            aptoTmp.error = jsonSonSon.getString("mensaje");
            aptoTmp.numero = jsonSonSon.getInt("inmueble");
            aptoTmp.fecha = jsonSonSon.getString("fecha");
            actualizar.add(aptoTmp);
        }
//        AptoHistorial aptoTmp = new AptoHistorial();
//        aptoTmp.error = PanelBotones.APERTURA_NO_PERMITIDA;
//        aptoTmp.numero = 10;
//        actualizar.add(aptoTmp);
        //Me llega un arreglo de json que tiene el historial sobre las alarmas, cada json tiene los atributos 
        //private String mensaje; XXX
        //  private String prioridad;
        //  private String unidadResidencial; XXX
        // private String inmueble;
        // private String dispositivo;
        // private Date fecha; XXX
//        String[] historialArregloBruto = content.toString().split("{");

//        ArrayList<AptoHistorial> arraylistAptoHistorial = new ArrayList<>();

//        for (String stringBrutoIndividual : historialArregloBruto) {
//            AptoHistorial historialActual = new AptoHistorial();
//
//            historialActual.numero= Integer.parseInt(stringBrutoIndividual.substring(stringBrutoIndividual.indexOf("\"unidadResidencial\" : \"") + 1, stringBrutoIndividual.indexOf("\"")));
//            historialActual.error= stringBrutoIndividual.substring(stringBrutoIndividual.indexOf("\"mensaje\" : \"") + 1, stringBrutoIndividual.indexOf("\""));
//            historialActual.fecha= stringBrutoIndividual.substring(stringBrutoIndividual.indexOf("\"fecha\" : \"") + 1, stringBrutoIndividual.indexOf("\""));
//
//            arraylistAptoHistorial.add(historialActual);
//        }
//
        in.close();
        con.disconnect();
        
        return actualizar;
    }

    public String enviar(String usuario, String contrasena) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/cuenta/login");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());

        String cuerpo = "{ \"email\": \"" + usuario + "\", \"password\": \"" + contrasena +"\"}";


        out.writeBytes(cuerpo);
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
