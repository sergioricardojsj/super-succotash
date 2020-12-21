package dev.sergior.cursomc.domain;

public enum TipoCliente {

    PESSOA_FISICA(1, "PF"),
    PESSOA_JURIDICA(2,"PJ");

    private int cod;
    private String sigla;

    TipoCliente(int cod, String sigla) {
        this.cod = cod;
        this.sigla = sigla;
    }

    public int getCod() {
        return cod;
    }

    public String getSigla() {
        return sigla;
    }

    public static TipoCliente toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (TipoCliente tipoCliente : TipoCliente.values()) {
            if (codigo.equals(tipoCliente.getCod()))
                return tipoCliente;
        }

        throw new IllegalArgumentException("ID Inválido: " + codigo);
    }

}
