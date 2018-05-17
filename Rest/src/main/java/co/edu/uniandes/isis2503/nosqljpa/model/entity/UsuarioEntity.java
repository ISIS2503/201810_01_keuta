/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @author sp.joven
 */
@Entity
@Table(name = "USUARIOS")
public class UsuarioEntity  implements Serializable{

    @Id
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
  
    public UsuarioEntity() {
        
    }

    public UsuarioEntity(String nombre, String correo, String telefono, String id) {
        this.id=id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
 
    }
public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
