package dev.sergior.cursomc.domain;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private Integer codigo;
    private String estado;

    EstadoPagamento(Integer codigo, String estado) {
        this.codigo = codigo;
        this.estado = estado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static EstadoPagamento toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (EstadoPagamento estado: EstadoPagamento.values()) {
            if (codigo.equals(estado.getCodigo()))
                return estado;
        }

        throw new IllegalArgumentException("Id inválido!");
    }

}
