package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrdenDTO {

    private String idUnidad;
    private String idInmueble;
    private String idOrden;
    private int idClave;
    private int clave;
    private Date fechaYhoraInicial;
    private Date fechaYhoraFinal;
    public boolean estaActivo;

    public OrdenDTO() {

    }

    public OrdenDTO(String idUnidad, String idInmueble, String idOrden, int idClave, int clave, Date fechaYhoraInicial, Date fechaYhoraFinal) {
        this.idUnidad = idUnidad;
        this.idInmueble = idInmueble;
        this.idOrden = idOrden;
        this.idClave = idClave;
        this.clave = clave;
        this.fechaYhoraInicial = fechaYhoraInicial;
        this.fechaYhoraFinal = fechaYhoraFinal;
        this.estaActivo = false;
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

    public Date getFechaYhoraInicial() {
        return fechaYhoraInicial;
    }

    public void setFechaYhoraInicial(Date fechaYhoraInicial) {
        this.fechaYhoraInicial = fechaYhoraInicial;
    }

    public Date getFechaYhoraFinal() {
        return fechaYhoraFinal;
    }

    public void setFechaYhoraFinal(Date fechaYhoraFinal) {
        this.fechaYhoraFinal = fechaYhoraFinal;
    }

    public boolean getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

}
