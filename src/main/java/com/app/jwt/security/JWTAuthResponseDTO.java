package com.app.jwt.security;

public class JWTAuthResponseDTO {
    private String tokenDeacceso;
    private String tipoDeToken="Bearer";

    public JWTAuthResponseDTO(String tokenDeacceso) {
        this.tokenDeacceso = tokenDeacceso;
    }

    public String getTokenDeacceso() {
        return tokenDeacceso;
    }

    public void setTokenDeacceso(String tokenDeacceso) {
        this.tokenDeacceso = tokenDeacceso;
    }

    public String getTipoDeToken() {
        return tipoDeToken;
    }

    public void setTipoDeToken(String tipoDeToken) {
        this.tipoDeToken = tipoDeToken;
    }
}
