package data.administracion;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioData {
    private Connection conn;


    public UsuarioData(Connection conn) {
        this.conn = conn;
    }
    public long crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, apellido, email, password, rol, estado) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getRol());
            ps.setString(6, usuario.getEstado());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setIdUsuario(rs.getLong(1));
                    }
                }
            }
        }
        return usuario.getIdUsuario();
    }


    public Usuario buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE idUsuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getLong("idUsuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getString("estado"));
                    return usuario;
                }
                return null;
            }
        }
    }

    public boolean eliminar(long idUsuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, email = ?, password = ?, rol = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.setLong(5, usuario.getIdUsuario());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT idUsuario, nombre, apellido, email, rol, estado FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getLong("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
}
