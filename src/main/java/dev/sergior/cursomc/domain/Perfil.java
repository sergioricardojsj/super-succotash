package dev.sergior.cursomc.domain;

public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private Integer cod;
    private String texto;

    Perfil(Integer numero, String texto) {
        this.cod = numero;
        this.texto = texto;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public static Perfil toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (Perfil perfil : Perfil.values()) {
            if (codigo.equals(perfil.getCod()))
                return perfil;
        }

        throw new IllegalArgumentException("ID Inválido: " + codigo);
    }

}
