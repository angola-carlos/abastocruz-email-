package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private Long idVenta;
    private LocalDateTime fechaVenta;
    private BigDecimal totalVenta;
    private BigDecimal impuesto;
    private BigDecimal descuentoAplicado;
    private String estadoVenta;
    private List<DetalleVenta> detalles;
    private Long idCliente;
    private Long idUsuario;

    public Venta() {
    }

    public Venta(Long idVenta, LocalDateTime fechaVenta, BigDecimal totalVenta, BigDecimal impuesto, BigDecimal descuentoAplicado, String estadoVenta, Long idCliente, Long idUsuario) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
        this.impuesto = impuesto;
        this.descuentoAplicado = descuentoAplicado;
        this.estadoVenta = estadoVenta;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(BigDecimal descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public String getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(String estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
