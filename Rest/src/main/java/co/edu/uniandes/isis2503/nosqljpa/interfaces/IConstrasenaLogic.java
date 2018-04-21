package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;

public interface IConstrasenaLogic {
    OrdenDTO add(OrdenDTO dto);

    OrdenDTO update(OrdenDTO dto);

    void delete(String idUnidad, String idInmueble, String idDispositivo, String numclave);

    void deleteAll(String idUnidad, String idInmueble, String idDispositivo);
}
