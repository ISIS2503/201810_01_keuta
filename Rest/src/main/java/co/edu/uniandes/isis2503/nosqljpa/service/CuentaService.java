/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CuentaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.LoginDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UpdateCuentaDTO;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.auth0.jwt.JWT;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

/**
 *
 * @author da.ramirezv
 */
@Path("/cuenta")
@Produces(MediaType.APPLICATION_JSON)
public class CuentaService {

    public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik5FWTRNMFE1TlRZMU1UTkVPVEkyUmpkRE1FSXlNVGxCTnpRNVJqQkRPVGREUVRjMlEwRTFOZyJ9.eyJpc3MiOiJodHRwczovL2lzaXMyNTAzLWRhcmFtaXJlenYuYXV0aDAuY29tLyIsInN1YiI6Imh3bWcybERnaUQzQTJqZUpHeEh1TmtwcUxFVFVkT2pSQGNsaWVudHMiLCJhdWQiOiJodHRwczovL2lzaXMyNTAzLWRhcmFtaXJlenYuYXV0aDAuY29tL2FwaS92Mi8iLCJpYXQiOjE1MjY1OTIzODUsImV4cCI6bnVsbCwiYXpwIjoiaHdtZzJsRGdpRDNBMmplSkd4SHVOa3BxTEVUVWRPalIiLCJzY29wZSI6InJlYWQ6Y2xpZW50X2dyYW50cyBjcmVhdGU6Y2xpZW50X2dyYW50cyBkZWxldGU6Y2xpZW50X2dyYW50cyB1cGRhdGU6Y2xpZW50X2dyYW50cyByZWFkOnVzZXJzIHVwZGF0ZTp1c2VycyBkZWxldGU6dXNlcnMgY3JlYXRlOnVzZXJzIHJlYWQ6dXNlcnNfYXBwX21ldGFkYXRhIHVwZGF0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgZGVsZXRlOnVzZXJzX2FwcF9tZXRhZGF0YSBjcmVhdGU6dXNlcnNfYXBwX21ldGFkYXRhIGNyZWF0ZTp1c2VyX3RpY2tldHMgcmVhZDpjbGllbnRzIHVwZGF0ZTpjbGllbnRzIGRlbGV0ZTpjbGllbnRzIGNyZWF0ZTpjbGllbnRzIHJlYWQ6Y2xpZW50X2tleXMgdXBkYXRlOmNsaWVudF9rZXlzIGRlbGV0ZTpjbGllbnRfa2V5cyBjcmVhdGU6Y2xpZW50X2tleXMgcmVhZDpjb25uZWN0aW9ucyB1cGRhdGU6Y29ubmVjdGlvbnMgZGVsZXRlOmNvbm5lY3Rpb25zIGNyZWF0ZTpjb25uZWN0aW9ucyByZWFkOnJlc291cmNlX3NlcnZlcnMgdXBkYXRlOnJlc291cmNlX3NlcnZlcnMgZGVsZXRlOnJlc291cmNlX3NlcnZlcnMgY3JlYXRlOnJlc291cmNlX3NlcnZlcnMgcmVhZDpkZXZpY2VfY3JlZGVudGlhbHMgdXBkYXRlOmRldmljZV9jcmVkZW50aWFscyBkZWxldGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGNyZWF0ZTpkZXZpY2VfY3JlZGVudGlhbHMgcmVhZDpydWxlcyB1cGRhdGU6cnVsZXMgZGVsZXRlOnJ1bGVzIGNyZWF0ZTpydWxlcyByZWFkOnJ1bGVzX2NvbmZpZ3MgdXBkYXRlOnJ1bGVzX2NvbmZpZ3MgZGVsZXRlOnJ1bGVzX2NvbmZpZ3MgcmVhZDplbWFpbF9wcm92aWRlciB1cGRhdGU6ZW1haWxfcHJvdmlkZXIgZGVsZXRlOmVtYWlsX3Byb3ZpZGVyIGNyZWF0ZTplbWFpbF9wcm92aWRlciBibGFja2xpc3Q6dG9rZW5zIHJlYWQ6c3RhdHMgcmVhZDp0ZW5hbnRfc2V0dGluZ3MgdXBkYXRlOnRlbmFudF9zZXR0aW5ncyByZWFkOmxvZ3MgcmVhZDpzaGllbGRzIGNyZWF0ZTpzaGllbGRzIGRlbGV0ZTpzaGllbGRzIHVwZGF0ZTp0cmlnZ2VycyByZWFkOnRyaWdnZXJzIHJlYWQ6Z3JhbnRzIGRlbGV0ZTpncmFudHMgcmVhZDpndWFyZGlhbl9mYWN0b3JzIHVwZGF0ZTpndWFyZGlhbl9mYWN0b3JzIHJlYWQ6Z3VhcmRpYW5fZW5yb2xsbWVudHMgZGVsZXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRzIGNyZWF0ZTpndWFyZGlhbl9lbnJvbGxtZW50X3RpY2tldHMgcmVhZDp1c2VyX2lkcF90b2tlbnMgY3JlYXRlOnBhc3N3b3Jkc19jaGVja2luZ19qb2IgZGVsZXRlOnBhc3N3b3Jkc19jaGVja2luZ19qb2IgcmVhZDpjdXN0b21fZG9tYWlucyBkZWxldGU6Y3VzdG9tX2RvbWFpbnMgY3JlYXRlOmN1c3RvbV9kb21haW5zIHJlYWQ6ZW1haWxfdGVtcGxhdGVzIGNyZWF0ZTplbWFpbF90ZW1wbGF0ZXMgdXBkYXRlOmVtYWlsX3RlbXBsYXRlcyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.fohFRy9Esf9Jzlwk96SNYcioHJ5A8BZpwyC4A5pucOF2MqTqOWXC_tHckjqNJPLEV8xxdaZJ-kDSx9WRsNxUDDUoHpRybQThV5dFgED1zinKhyr5jN7fk-3qFDJ50F5bkxyD-8tDM15VN4Z_RK_OY95ozKfmR0mnEZR0lCR8fKQA0F4nbYGz7Je9_nkTSMJ-pksKETd6OVuT1fl3x8Yo-Th3Vna3kXoB6-lmAhVQdlKbzQlRUZUX-JdJee_kuF_vDBBmfOyOorxFjnqnIfsuwdejWWPoLUvnvfxZUGpt0g4aoU0q8txeuR_EpaFOpjiQpNB0pFhtNvUSmkJwURcmCg";

    @POST
    public String agregarCuenta(CuentaDTO dto) throws Exception {

        String url = "https://isis2503-daramirezv.auth0.com/api/v2/users";
        String urlParameters = "user_id=" + dto.getUser_id() + "&email=" + dto.getEmail() + "&password=" + dto.getPassword() + "&email_verified=" + dto.isEmail_verified() + "&connection=" + dto.getConnection();
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection con;

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + TOKEN);
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());
            return content.toString();

        } finally {

        }
    }

    @PUT
    @Path("/{id}")
    public String modificarCuenta(@PathParam("id") String id, UpdateCuentaDTO dto) throws Exception {

        String url = "https://isis2503-daramirezv.auth0.com/api/v2/users/";

        id = id.replace("|", "%7C");
        HttpClient client = new DefaultHttpClient();
        HttpPatch patch = new HttpPatch(url + id);
        // add header
        patch.setHeader("Content-Type", "application/x-www-form-urlencoded");
        patch.setHeader("Authorization", "Bearer " + TOKEN);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("email_verified", "" + dto.isEmail_verified()));

        patch.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(patch);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
        return result.toString();
    }

    @GET
    @Path("/{id}")
    public String obtenerCuenta(@PathParam("id") String id) throws Exception {

        String url = "https://isis2503-daramirezv.auth0.com/api/v2/users/" + id;
        HttpURLConnection con;

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + TOKEN);

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());
            return content.toString();

        } finally {

        }
    }

    @GET
    public String todasLasCuentas() throws Exception {

        String url = "https://isis2503-daramirezv.auth0.com/api/v2/users";
        HttpURLConnection con;

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + TOKEN);

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());
            return content.toString();

        } finally {

        }
    }

    @DELETE
    @Path("/{id}")
    public void borrarCuenta(@PathParam("id") String id) throws Exception {

        String url = "https://isis2503-daramirezv.auth0.com/api/v2/users/" + id;
        HttpURLConnection con;

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("DELETE");
            con.setRequestProperty("Authorization", "Bearer " + TOKEN);

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

        } finally {

        }
    }

    @POST
    @Path("/login")
    public LoginDTO logIn(CuentaDTO cuentaDTO, @Context HttpServletResponse httpServletResponse ) throws WebApplicationException{
        try {
            String url = "https://isis2503-daramirezv.auth0.com/oauth/token";
            HttpURLConnection con;
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            JSONObject tomJsonObj = new JSONObject();
            tomJsonObj.put("grant_type", "http://auth0.com/oauth/grant-type/password-realm");
            tomJsonObj.put("username", cuentaDTO.getEmail());
            tomJsonObj.put("password", cuentaDTO.getPassword());
            tomJsonObj.put("audience", "uniandes.edu.co/thermalcomfort");
            tomJsonObj.put("scope", "openid");
            tomJsonObj.put("client_id", "6SGnWSj-Us9t63VJHwgJC-S223qCvqSM");
            tomJsonObj.put("client_secret", "m8sfjctqF-w8Y6i7543c6X2yokMRjopiVARfbUNg5C97Zwi1TIjKxJeLgGoRJ61Y");
            tomJsonObj.put("realm", "Username-Password-Authentication");
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

            LoginDTO loginDTO = new LoginDTO();

            if (status != 200) {
                throw new WebApplicationException(content.toString(), status);
            } else {
                JSONObject contentJSON = new JSONObject(content.toString());
                String token = contentJSON.getString("id_token");

                List<String> roles = new ArrayList();
                roles = JWT.decode(token).getClaim("http://thermalcomfort/roles").asList(String.class);
                if (!roles.contains(AuthorizationFilter.Role.seguridad.toString())){
                    throw new WebApplicationException("No es usuario de seguridad.", 403);
                } else {
                    String tokenType = contentJSON.getString("token_type");
                    httpServletResponse.addHeader(HttpHeaders.AUTHORIZATION,tokenType + " " + token);
                }
                loginDTO.setRoles(roles.toArray(new String[0]));
            }

            con.disconnect();

            return loginDTO;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new WebApplicationException("Error", 500);
    }

    @POST
    @Path("/logout")
    public void logout(@Context HttpServletResponse httpServletResponse){
//        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION,null);
    }

}
