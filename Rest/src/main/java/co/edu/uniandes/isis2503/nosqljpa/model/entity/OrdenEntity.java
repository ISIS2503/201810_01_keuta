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
package co.edu.uniandes.isis2503.nosqljpa.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ca.mendoza968
 */
@Entity
@Table(name = "ORDENN")
public class OrdenEntity implements Serializable {

    @Id
    private int idClave;
    private String idUnidad;
    private String idInmueble;
    private String idOrden;
    private int clave;
    private String fechaYhoraInicial;
    private String fechaYhoraFinal;
    public boolean estaActivo;
    
    public OrdenEntity() {
        
    }

    public OrdenEntity(String idUnidad, String idInmueble, String idOrden, int idClave, int clave, String fechaYhoraInicial, String fechaYhoraFinal, boolean estaActivo) {
        this.idClave=idClave;
        this.idUnidad = idUnidad;
        this.idInmueble = idInmueble;
        this.idOrden = idOrden;
        this.clave = clave;
        this.fechaYhoraInicial = fechaYhoraInicial;
        this.fechaYhoraFinal = fechaYhoraFinal;
        this.estaActivo = estaActivo;
    }
public String getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(String idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(String idInmueble) {
        this.idInmueble = idInmueble;
    }

    public int getIdClave() {
        return idClave;
    }

    public void setIdClave(int idClave) {
        this.idClave = idClave;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getFechaYhoraInicial() {
        return fechaYhoraInicial;
    }

    public void setFechaYhoraInicial(String fechaYhoraInicial) {
        this.fechaYhoraInicial = fechaYhoraInicial;
    }

    public String getFechaYhoraFinal() {
        return fechaYhoraFinal;
    }

    public void setFechaYhoraFinal(String fechaYhoraFinal) {
        this.fechaYhoraFinal = fechaYhoraFinal;
    }
    
     public boolean getEstaActivo() {
        return estaActivo;
    }
    
    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }
    
}