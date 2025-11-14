package model;

public class Comando {
    private Long idComando;
    private String comando;
    private String descripcion;
    private String formato;

    public Long getIdComando() {
        return idComando;
    }

    public void setIdComando(Long idComando) {
        this.idComando = idComando;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}
