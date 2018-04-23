/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author da.ramirezv
 */
@XmlRootElement
public class CuentaDTO {
    
    private String user_id;
    private String email;
    private String password;
    private boolean email_verified;
    private String connection = "Username-Password-Authentication";

    public CuentaDTO() {
    }

    public CuentaDTO(String user_id, String email, String password, boolean email_verified) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.email_verified = email_verified;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
