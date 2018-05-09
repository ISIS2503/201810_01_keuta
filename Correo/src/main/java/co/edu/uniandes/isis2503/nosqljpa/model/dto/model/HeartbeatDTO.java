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
public class HeartbeatDTO {
    
    private Long timestamp;
    private String idhub;

    public HeartbeatDTO(Long timestamp, String idhub) {
        this.timestamp = timestamp;
        this.idhub = idhub;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdhub() {
        return idhub;
    }

    public void setIdhub(String idhub) {
        this.idhub = idhub;
    }
}
