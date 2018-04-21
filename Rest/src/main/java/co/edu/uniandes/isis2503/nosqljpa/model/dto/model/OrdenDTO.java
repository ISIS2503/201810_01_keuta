package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrdenDTO {
    private String idUnidad;
    private String idInmueble;
    private String idCerradura;
    private int idClave;
    private int clave;

    public OrdenDTO () {

    }

    public OrdenDTO(String idUnidad, String idInmueble, String idCerradura, int idClave, int clave) {
        this.idUnidad = idUnidad;
        this.idInmueble = idInmueble;
        this.idCerradura = idCerradura;
        this.idClave = idClave;
        this.clave = clave;
    }

    public String getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(String idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getIdCerradura() {
        return idCerradura;
    }

    public void setIdCerradura(String idCerradura) {
        this.idCerradura = idCerradura;
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
}
