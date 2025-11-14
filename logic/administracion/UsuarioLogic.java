package logic.administracion;

import data.administracion.UsuarioData;
import model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioLogic {
    private UsuarioData usuarioData;

    public UsuarioLogic (Connection connection){
        this.usuarioData = new UsuarioData(connection);
    }

    /*public Usuario login(String email, String password) throws SQLException {
        Usuario user = usuarioData.obtenerPorEmail(email);
        if (user == null) return null;
        if (!user.getPassword().equals(password)) return null;
        return user;
    }*/


    public Usuario registrarUsuario(Usuario usuario) throws SQLException {
        Long id = usuarioData.crear(usuario);
        usuario.setIdUsuario(id);
        return usuario;
    }


    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        return usuarioData.actualizar(usuario);
    }


    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        return usuarioData.eliminar(idUsuario);
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioData.listarTodos();
    }
}
