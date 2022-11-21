package com.app.jwt.service;

import com.app.jwt.dto.PublicacionDTO;
import com.app.jwt.dto.PublicacionRespuesta;


public interface PublicacionServicio {
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int tamPagina, String ordenarPor, String sortDir);
    public PublicacionDTO obtenerPublicacionPorID(long id);
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id);
    public void elimnarPublicacion(long id);

}
