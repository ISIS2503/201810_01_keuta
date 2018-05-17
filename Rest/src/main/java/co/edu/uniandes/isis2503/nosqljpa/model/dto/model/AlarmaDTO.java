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

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ca.mendoza968
 */
@XmlRootElement
public class AlarmaDTO {
    
     private Long idMensaje;
    private String mensaje;
    private String prioridad;
    private String unidadResidencial;
    private String inmueble;
    private String dispositivo;
    private String fecha;
    private String barrio;
    private String mes;

    public AlarmaDTO() {
        
    }

    public AlarmaDTO(Long idMensaje,String mensaje, String prioridad, String unidadResidencial, String inmueble, String dispositivo, String fecha, String barrio) {
        this.idMensaje=idMensaje;
        this.mensaje = mensaje;
        this.prioridad = prioridad;
        this.unidadResidencial = unidadResidencial;
        this.inmueble = inmueble;
        this.dispositivo = dispositivo;
        this.fecha= fecha;
        this.mes = fecha.substring(3, 4);
        System.out.println("MESSSSSSSSSS: "+mes);
        this.barrio = barrio;
        
    }
     public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes
                ;
    }
 public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
     public Long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
     public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getUnidadResidencial() {
        return unidadResidencial;
    }

    public void setUnidadResidencial(String unidadResidencial) {
        this.unidadResidencial = unidadResidencial;
    }

    public String getInmueble() {
        return inmueble;
    }

    public void setInmueble(String inmueble) {
        this.inmueble = inmueble;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
}