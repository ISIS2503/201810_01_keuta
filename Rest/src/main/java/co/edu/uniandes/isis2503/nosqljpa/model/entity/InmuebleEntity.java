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

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CerraduraDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.HubDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ca.mendoza968
 */
@Entity
@Table(name = "INMUEBLE")
public class InmuebleEntity implements Serializable {

    @Id
    private String id;
    private Integer numeroInmueble;
    private String idUnidadResidencial;
    private String nombrePropietario;

    public InmuebleEntity()
    {
        
    }
    public InmuebleEntity(String id, Integer numeroInmueble, String idUnidadResidencial, String nombrePropietario)
    {
        this.id = id;
        this.numeroInmueble = numeroInmueble;
        this.nombrePropietario = nombrePropietario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumeroInmueble() {
        return numeroInmueble;
    }

    public void setNumeroInmueble(Integer numeroInmueble) {
        this.numeroInmueble = numeroInmueble;
    }

    public String getIdUnidadResidencial() {
        return idUnidadResidencial;
    }

    public void setIdUnidadResidencial(String idUnidadResidencial) {
        this.idUnidadResidencial = idUnidadResidencial;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }
}