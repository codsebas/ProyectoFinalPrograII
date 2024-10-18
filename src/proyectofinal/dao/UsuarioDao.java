/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyectofinal.modelos.ModeloUsuario;
import proyectofinal.sql.Conector;

/**
 *
 * @author gerso
 */
public class UsuarioDao {
    
    // Método para registrar un nuevo usuario
    public void registrarUsuario(ModeloUsuario usuario) {
        String sql = "INSERT INTO usuarios (usuario, password, rol) VALUES (?, ?, ?)";
        
        try (Connection conn = new Conector().conectar();  
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getRol());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para obtener un usuario por rol
    public ModeloUsuario obtenerUsuarioPorRol(String rol) {
        ModeloUsuario modeloUsuario = null;
        String sql = "SELECT * FROM usuarios WHERE rol = ?";
        
        try (Connection conn = new Conector().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rol);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                modeloUsuario = new ModeloUsuario();
                modeloUsuario.setUsuario(rs.getString("usuario"));
                modeloUsuario.setPassword(rs.getString("password"));
                modeloUsuario.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return modeloUsuario;
    }
    
  
    public ModeloUsuario validarUsuario(String usuario, String password) {
        ModeloUsuario modeloUsuario = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
        
        try (Connection conn = new Conector().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                modeloUsuario = new ModeloUsuario();
                modeloUsuario.setUsuario(rs.getString("usuario"));
                modeloUsuario.setPassword(rs.getString("password"));
                modeloUsuario.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return modeloUsuario; 
    }
}