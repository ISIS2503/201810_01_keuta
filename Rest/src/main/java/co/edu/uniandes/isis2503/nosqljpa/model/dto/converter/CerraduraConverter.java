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

import co.edu.uniandes.isis2503.nosqljpa.interfaces.ICerraduraConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CerraduraDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.CerraduraEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ca.mendoza968
 */
public class CerraduraConverter implements ICerraduraConverter {

    public static ICerraduraConverter CONVERTER = new CerraduraConverter();

    public CerraduraConverter() {
    }

    @Override
    public CerraduraDTO entityToDto(CerraduraEntity entity) {
        CerraduraDTO dto = new CerraduraDTO();
        dto.setId(entity.getId());
        dto.setIdentificador(entity.getIdentificador());
        dto.setInmueble(entity.getInmueble());
        dto.setUnidadResidencial(entity.getUnidadResidencial());
        return dto;
    }

    @Override
    public CerraduraEntity dtoToEntity(CerraduraDTO dto) {
        CerraduraEntity entity = new CerraduraEntity();
        entity.setId(dto.getId());
        entity.setIdentificador(dto.getIdentificador());
        entity.setInmueble(dto.getInmueble());
        entity.setUnidadResidencial(dto.getUnidadResidencial());
        return entity;
    }

    @Override
    public List<CerraduraDTO> listEntitiesToListDTOs(List<CerraduraEntity> entities) {
        ArrayList<CerraduraDTO> dtos = new ArrayList<>();
        for (CerraduraEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<CerraduraEntity> listDTOsToListEntities(List<CerraduraDTO> dtos) {
        ArrayList<CerraduraEntity> entities = new ArrayList<>();
        for (CerraduraDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
