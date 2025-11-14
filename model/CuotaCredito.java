package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CuotaCredito {
    private Long idCuota;
    private LocalDateTime fechaCuota;
    private BigDecimal montoCuota;
    private String esMora;
    private Long idCredito;

    public Long getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(Long idCuota) {
        this.idCuota = idCuota;
    }

    public LocalDateTime getFechaCuota() {
        return fechaCuota;
    }

    public void setFechaCuota(LocalDateTime fechaCuota) {
        this.fechaCuota = fechaCuota;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public String getEsMora() {
        return esMora;
    }

    public void setEsMora(String esMora) {
        this.esMora = esMora;
    }

    public Long getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(Long idCredito) {
        this.idCredito = idCredito;
    }
}
