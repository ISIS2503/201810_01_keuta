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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ca.mendoza968
 */
@Entity
@Table(name = "CERRADURA")
public class CerraduraEntity implements Serializable {

    @Id
    private String id;

    private String identificador;
    
    private String unidadResidencial;
    
    private String inmueble;
    private String clave1;
    private String clave2;
    private String clave3;
    private String clave4;
    
    public CerraduraEntity() {
        
    }

    public CerraduraEntity(String id, String identificador, String idUnidadResidencial, String idInmueble, String clave1, String clave2, String clave3, String clave4) {
        this.id = id;
        this.identificador = identificador;
        this.unidadResidencial = idUnidadResidencial;
        this.inmueble = idInmueble;
        this.clave1=clave1;
        this.clave2=clave2;
        this.clave3=clave3;
        this.clave4=clave4;
    }
     public String getClave4() {
        return clave4;
    }

    public void setClave4(String clave4) {
        this.clave4 = clave4;
    }
     public String getClave3() {
        return clave3;
    }

    public void setClave3(String clave3) {
        this.clave3 = clave3;
    }
 public String getClave2() {
        return clave2;
    }

    public void setClave2(String clave2) {
        this.clave2 = clave2;
    }
     public String getClave1() {
        return clave1;
    }

    public void setClave1(String clave1) {
        this.clave1 = clave1;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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
}