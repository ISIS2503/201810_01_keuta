/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CuentaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UpdateCuentaDTO;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author da.ramirezv
 */
@Path("/cuenta")
@Produces(MediaType.APPLICATION_JSON)
public class CuentaService {
    
    public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik5FWTRNMFE1TlRZMU1UTkVPVEkyUmpkRE1FSXlNVGxCTnpRNVJqQkRPVGREUVRjMlEwRTFOZyJ9.eyJpc3MiOiJodHRwczovL2lzaXMyNTAzLWRhcmFtaXJlenYuYXV0aDAuY29tLyIsInN1YiI6Imh3bWcybERnaUQzQTJqZUpHeEh1TmtwcUxFVFVkT2pSQGNsaWVudHMiLCJhdWQiOiJodHRwczovL2lzaXMyNTAzLWRhcmFtaXJlenYuYXV0aDAuY29tL2FwaS92Mi8iLCJpYXQiOjE1MjQ0MTIxNTYsImV4cCI6MTUyNDQ5ODU1NiwiYXpwIjoiaHdtZzJsRGdpRDNBMmplSkd4SHVOa3BxTEVUVWRPalIiLCJzY29wZSI6InJlYWQ6Y2xpZW50X2dyYW50cyBjcmVhdGU6Y2xpZW50X2dyYW50cyBkZWxldGU6Y2xpZW50X2dyYW50cyB1cGRhdGU6Y2xpZW50X2dyYW50cyByZWFkOnVzZXJzIHVwZGF0ZTp1c2VycyBkZWxldGU6dXNlcnMgY3JlYXRlOnVzZXJzIHJlYWQ6dXNlcnNfYXBwX21ldGFkYXRhIHVwZGF0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgZGVsZXRlOnVzZXJzX2FwcF9tZXRhZGF0YSBjcmVhdGU6dXNlcnNfYXBwX21ldGFkYXRhIGNyZWF0ZTp1c2VyX3RpY2tldHMgcmVhZDpjbGllbnRzIHVwZGF0ZTpjbGllbnRzIGRlbGV0ZTpjbGllbnRzIGNyZWF0ZTpjbGllbnRzIHJlYWQ6Y2xpZW50X2tleXMgdXBkYXRlOmNsaWVudF9rZXlzIGRlbGV0ZTpjbGllbnRfa2V5cyBjcmVhdGU6Y2xpZW50X2tleXMgcmVhZDpjb25uZWN0aW9ucyB1cGRhdGU6Y29ubmVjdGlvbnMgZGVsZXRlOmNvbm5lY3Rpb25zIGNyZWF0ZTpjb25uZWN0aW9ucyByZWFkOnJlc291cmNlX3NlcnZlcnMgdXBkYXRlOnJlc291cmNlX3NlcnZlcnMgZGVsZXRlOnJlc291cmNlX3NlcnZlcnMgY3JlYXRlOnJlc291cmNlX3NlcnZlcnMgcmVhZDpkZXZpY2VfY3JlZGVudGlhbHMgdXBkYXRlOmRldmljZV9jcmVkZW50aWFscyBkZWxldGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGNyZWF0ZTpkZXZpY2VfY3JlZGVudGlhbHMgcmVhZDpydWxlcyB1cGRhdGU6cnVsZXMgZGVsZXRlOnJ1bGVzIGNyZWF0ZTpydWxlcyByZWFkOnJ1bGVzX2NvbmZpZ3MgdXBkYXRlOnJ1bGVzX2NvbmZpZ3MgZGVsZXRlOnJ1bGVzX2NvbmZpZ3MgcmVhZDplbWFpbF9wcm92aWRlciB1cGRhdGU6ZW1haWxfcHJvdmlkZXIgZGVsZXRlOmVtYWlsX3Byb3ZpZGVyIGNyZWF0ZTplbWFpbF9wcm92aWRlciBibGFja2xpc3Q6dG9rZW5zIHJlYWQ6c3RhdHMgcmVhZDp0ZW5hbnRfc2V0dGluZ3MgdXBkYXRlOnRlbmFudF9zZXR0aW5ncyByZWFkOmxvZ3MgcmVhZDpzaGllbGRzIGNyZWF0ZTpzaGllbGRzIGRlbGV0ZTpzaGllbGRzIHVwZGF0ZTp0cmlnZ2VycyByZWFkOnRyaWdnZXJzIHJlYWQ6Z3JhbnRzIGRlbGV0ZTpncmFudHMgcmVhZDpndWFyZGlhbl9mYWN0b3JzIHVwZGF0ZTpndWFyZGlhbl9mYWN0b3JzIHJlYWQ6Z3VhcmRpYW5fZW5yb2xsbWVudHMgZGVsZXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRzIGNyZWF0ZTpndWFyZGlhbl9lbnJvbGxtZW50X3RpY2tldHMgcmVhZDp1c2VyX2lkcF90b2tlbnMgY3JlYXRlOnBhc3N3b3Jkc19jaGVja2luZ19qb2IgZGVsZXRlOnBhc3N3b3Jkc19jaGVja2luZ19qb2IgcmVhZDpjdXN0b21fZG9tYWlucyBkZWxldGU6Y3VzdG9tX2RvbWFpbnMgY3JlYXRlOmN1c3RvbV9kb21haW5zIHJlYWQ6ZW1haWxfdGVtcGxhdGVzIGNyZWF0ZTplbWFpbF90ZW1wbGF0ZXMgdXBkYXRlOmVtYWlsX3RlbXBsYXRlcyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.TVri9WSYGWspRsdg8d618IyEhcdYSb1gV_GoYU8BEVgoZCCY2YY53Ksa2Fs9_cbdANHuRIsB5o2NpMeCE41Mzd64SEeoDIG-5UU7JVkHjX5lCgpqyDkPKH6D0TldYk5cDws7CTk8keOuKGMOqt2GkUqll7IRwNPMC2WzeFffSnyYGVkCCqJ9WYQufDo32vcRO2jh8tOqZ_Bs_NRIvTMRWkbdcY_BEo7yhpz23vXZg_sidehVC3UHJTxtagvzv5eTG71YB4sV9kYEx0nIMDoKK_qqhuVj8T-CoYyoJTGoI_NC92VFxL_4qmYoORkWJKUICHPHdO6CpWaq9kYHLSMKWA";

    @POST
    public String agregarCuenta (CuentaDTO dto) throws Exception {

        String url = "https://isis2503-daramirezv.auth0.com/api/v2/users";
        String urlParameters = "user_id=" + dto.getUser_id() + "&email=" + dto.getEmail() + "&password=" + dto.getPassword() + "&verify_email=" + dto.isVerify_email() + "&connection=" + dto.getConnection();
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
    public String modificarCuenta (@PathParam("id") String id, UpdateCuentaDTO dto) throws Exception {
        
        String url = "https://isis2503-daramirezv.auth0.com/api/v2/users/" + id;
        
        String urlParameters = "email_verified=" + dto.isEmail_verified()+ "&password=" + dto.getPassword();
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection con;

        try {
            
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            
            con.setDoOutput(true);
            con.setRequestMethod("PUT");
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
    public void borrarCuenta(@PathParam("id") String id) throws Exception{
        
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

}
