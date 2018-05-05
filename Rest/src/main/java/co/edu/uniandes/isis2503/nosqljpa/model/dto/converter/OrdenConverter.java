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
package co.edu.uniandes.isis2503.nosqljpa.model.dto.converter;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IOrdenConverter;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IOrdenConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.OrdenEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ca.mendoza968
 */
public class OrdenConverter implements IOrdenConverter {

    public static IOrdenConverter CONVERTER = new OrdenConverter();

    public OrdenConverter() {
    }

    @Override
    public OrdenDTO entityToDto(OrdenEntity entity) {
        OrdenDTO dto = new OrdenDTO();
        dto.setIdClave(entity.getIdClave());
        dto.setIdUnidad(entity.getIdUnidad());
        dto.setIdInmueble(entity.getIdInmueble());
        dto.setIdOrden(entity.getIdOrden());
        dto.setClave(entity.getClave());
        dto.setFechaYhoraInicial(entity.getFechaYhoraInicial());
        dto.setFechaYhoraFinal(entity.getFechaYhoraFinal());
        dto.setEstaActivo(entity.getEstaActivo());
        return dto;
    }

    @Override
    public OrdenEntity dtoToEntity(OrdenDTO dto) {
        OrdenEntity entity = new OrdenEntity();
        entity.setIdClave(dto.getIdClave());
        entity.setIdUnidad(dto.getIdUnidad());
        entity.setIdInmueble(dto.getIdInmueble());
        entity.setIdOrden(dto.getIdOrden());
        entity.setClave(dto.getClave());
        entity.setFechaYhoraInicial(dto.getFechaYhoraInicial());
        entity.setFechaYhoraFinal(dto.getFechaYhoraFinal());
        entity.setEstaActivo(dto.getEstaActivo());
        return entity;
    }

    @Override
    public List<OrdenDTO> listEntitiesToListDTOs(List<OrdenEntity> entities) {
        ArrayList<OrdenDTO> dtos = new ArrayList<>();
        for (OrdenEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<OrdenEntity> listDTOsToListEntities(List<OrdenDTO> dtos) {
        ArrayList<OrdenEntity> entities = new ArrayList<>();
        for (OrdenDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
