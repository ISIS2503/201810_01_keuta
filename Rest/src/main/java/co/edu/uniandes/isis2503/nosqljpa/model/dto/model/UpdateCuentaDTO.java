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
public class UpdateCuentaDTO {
    
    private String password;
    private boolean email_verified;

    public UpdateCuentaDTO() {
        
    }

    public UpdateCuentaDTO(String password, boolean email_verified) {
        this.password = password;
        this.email_verified = email_verified;
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

    
}
