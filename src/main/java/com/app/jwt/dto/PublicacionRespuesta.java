package com.app.jwt.dto;

import com.app.jwt.model.Publicacion;

import java.util.List;

public class PublicacionRespuesta {

    private List<PublicacionDTO> contenido;
    private int numeroPagina;
    private int tamPagina;
    private long totElementos;
    private int totPaginas;
    private boolean ultima;

    public PublicacionRespuesta(){};

    public PublicacionRespuesta(List<PublicacionDTO> contenido, int numeroPagina, int tamPagina, long totElementos, int totPaginas, boolean ultima) {
        this.contenido = contenido;
        this.numeroPagina = numeroPagina;
        this.tamPagina = tamPagina;
        this.totElementos = totElementos;
        this.totPaginas = totPaginas;
        this.ultima = ultima;
    }

    public List<PublicacionDTO> getContenido() {
        return contenido;
    }

    public void setContenido(List<PublicacionDTO> contenido) {
        this.contenido = contenido;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public int getTamPagina() {
        return tamPagina;
    }

    public void setTamPagina(int tamPagina) {
        this.tamPagina = tamPagina;
    }

    public long getTotElementos() {
        return totElementos;
    }

    public void setTotElementos(long totElementos) {
        this.totElementos = totElementos;
    }

    public int getTotPaginas() {
        return totPaginas;
    }

    public void setTotPaginas(int totPaginas) {
        this.totPaginas = totPaginas;
    }

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }
}
