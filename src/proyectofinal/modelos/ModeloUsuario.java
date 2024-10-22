/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

/**
 *
 * @author gerso
 */
public class ModeloUsuario {
    
    private int id; 
    private String usuario;
  
    private String password;
    private String rol;

    public ModeloUsuario() {
    }

    public int getId() {
        return id; 
    }

    public void setId(int id) {
        this.id = id; 
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

   
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
    
    
}
