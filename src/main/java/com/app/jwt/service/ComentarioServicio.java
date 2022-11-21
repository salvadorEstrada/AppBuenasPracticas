package com.app.jwt.service;

import com.app.jwt.dto.ComentarioDTO;

import java.util.List;

public interface ComentarioServicio {
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO);
    //Cuando yo busque una publicación me liste con sus comentarios correspondientes
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId);
    //para buscar en el servicio
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId,Long comentarioId);
    //método para actualizar
    public ComentarioDTO actualizarComentario(Long publicacionId,Long comentarioId,ComentarioDTO solicitudDeComentario);

    public void  eliminarComentario(Long publicacionId, Long comentarioId);
}
