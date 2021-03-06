/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ca.mendoza968
 */
@XmlRootElement
public class CorreoDTO {
    private List<String> destinatarios;
    private String remitente;
    private String asunto;
    private String cuerpo;

    public CorreoDTO() {
        destinatarios = new ArrayList<>();
    }

    public CorreoDTO(List<String> pdestinatarios, String premitente, String pasunto, String pcuerpo) {
        this.destinatarios = pdestinatarios;
        this.remitente = premitente;
        this.asunto = pasunto;
        this.cuerpo = pcuerpo;
    }

    public List<String> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<String> pdestinatarios) {
        this.destinatarios = pdestinatarios;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String premitente) {
        this.remitente = premitente;
    }
    
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String pasunto) {
        this.asunto = pasunto;
    }
    
    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String pcuerpo) {
        this.cuerpo = pcuerpo;
    }
}