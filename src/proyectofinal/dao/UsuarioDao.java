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

public class UsuarioDao {
    

   public void registrarUsuario(String usuario, String password, String rol) {
        if (usuarioYaExiste(usuario)) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }
        
        String sql = "INSERT INTO usuarios (user_usuario, contrasena_user, rol) VALUES (?, ?, ?)";
        
        try (Connection conn = new Conector().conectar();  
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);  // Campo: user_usuario (PK)
            stmt.setString(2, password);  // Campo: contrasena_user
            stmt.setString(3, rol);       // Campo: rol
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Considera lanzar una excepción personalizada aquí
        }
    }

    // Método para verificar si el usuario ya existe
    private boolean usuarioYaExiste(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE user_usuario = ?";
        
        try (Connection conn = new Conector().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si el usuario existe
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        return false; // El usuario no existe
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
                modeloUsuario.setUsuario(rs.getString("user_usuario")); // Campo: user_usuario (PK)
                modeloUsuario.setPassword(rs.getString("contrasena_user")); // Campo: contrasena_user
                modeloUsuario.setRol(rs.getString("rol")); // Campo: rol
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        return modeloUsuario;
    }

    // Método para validar un usuario
    public ModeloUsuario validarUsuario(String usuario, String password) {
        ModeloUsuario modeloUsuario = null;
        String sql = "SELECT * FROM usuarios WHERE user_usuario = ? AND contrasena_user = ?";

        try (Connection conn = new Conector().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);  // Campo: user_usuario (PK)
            stmt.setString(2, password);  // Campo: contrasena_user
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                modeloUsuario = new ModeloUsuario();
                modeloUsuario.setUsuario(rs.getString("user_usuario")); // Campo: user_usuario (PK)
                modeloUsuario.setPassword(rs.getString("contrasena_user")); // Campo: contrasena_user
                modeloUsuario.setRol(rs.getString("rol")); // Campo: rol
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        return modeloUsuario; 
    }
}