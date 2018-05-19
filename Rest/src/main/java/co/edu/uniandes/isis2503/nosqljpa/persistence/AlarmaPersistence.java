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
package co.edu.uniandes.isis2503.nosqljpa.persistence;

import co.edu.uniandes.isis2503.nosqljpa.model.entity.AlarmaEntity;

import javax.persistence.Query;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *
 * @author da.ramirezv
 */
public class AlarmaPersistence extends Persistencer<AlarmaEntity, String>{

    public AlarmaPersistence(){
        this.entityClass = AlarmaEntity.class;
    }

    public List<AlarmaEntity> darAlarmasMesInmueble(int anio, int mes, String idInmueble){

        List<AlarmaEntity> entities;

        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.fecha >= :fechaIni " +
                "AND c.fecha < :fechaFin AND c.inmueble = :inmueble";
        Query query = entityManager.createQuery(queryString);
        LocalDateTime fechaIni;
        fechaIni = LocalDateTime.of(anio, mes, 1,0,0);
        query.setParameter("fechaIni", Timestamp.valueOf(fechaIni));
        LocalDateTime fechaFin;
        fechaFin = fechaIni.plus(1, ChronoUnit.MONTHS);
        query.setParameter("fechaFin", Timestamp.valueOf(fechaFin));
        query.setParameter("inmueble", idInmueble);

        entities = query.getResultList();
        return entities;

    }

    public List<AlarmaEntity> darAlarmasMesUnidadResidencial(int anio, int mes, String idUnidadResidencial){

        List<AlarmaEntity> entities;

        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.fecha >= :fechaIni " +
                "AND c.fecha < :fechaFin AND c.unidadResidencial = :unidadResidencial";
        Query query = entityManager.createQuery(queryString);
        LocalDateTime fechaIni;
        fechaIni = LocalDateTime.of(anio, mes, 1,0,0);
        query.setParameter("fechaIni", Timestamp.valueOf(fechaIni));
        LocalDateTime fechaFin;
        fechaFin = fechaIni.plus(1, ChronoUnit.MONTHS);
        query.setParameter("fechaFin", Timestamp.valueOf(fechaFin));
        query.setParameter("unidadResidencial", idUnidadResidencial);

        entities = query.getResultList();
        return entities;

    }

    public List<AlarmaEntity> darAlarmasTiempoReal(){

        List<AlarmaEntity> entities;

        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.fecha >= :fechaIni";
        Query query = entityManager.createQuery(queryString);
        LocalDateTime ahora = LocalDateTime.now();
        query.setParameter("fechaIni", Timestamp.valueOf(ahora.minus(1, ChronoUnit.MINUTES)));

        entities = query.getResultList();
        return entities;

    }

}