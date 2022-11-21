package com.app.jwt.service;

import com.app.jwt.dto.ComentarioDTO;
import com.app.jwt.exeption.BlogAppException;
import com.app.jwt.exeption.ResourceNotFoundException;
import com.app.jwt.model.Comentario;
import com.app.jwt.model.Publicacion;
import com.app.jwt.repository.ComentarioRepository;
import com.app.jwt.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioServicio {
    @Autowired
    private ModelMapper modelMapper; //Se definó en la clase principal del proyecto

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario =  mapearEntidad(comentarioDTO);
        Publicacion publicacion  = publicacionRepository.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("publicacion","publicacionId",publicacionId));
        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario= comentarioRepository.save(comentario);
        return mapearDTO(nuevoComentario);
    }


    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId) {
        List<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId);
        return comentarios.stream().map(comentario->mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion  = publicacionRepository.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("publicacion","id",publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(()-> new ResourceNotFoundException("comentario","id",comentarioId) );
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }
        return mapearDTO(comentario);
    }
    //ACTUALIZAR COMENTARIOS
    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId,Long comentarioId, ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion  = publicacionRepository.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("publicacion","id",publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(()-> new ResourceNotFoundException("comentario","id",comentarioId) );
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }
        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentario comentarioActualizado = comentarioRepository.save(comentario);

        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion  = publicacionRepository.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("publicacion","id",publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(()-> new ResourceNotFoundException("comentario","id",comentarioId) );
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }
        comentarioRepository.delete(comentario);
    }
    //Los siguientes dos métodos funcionan en base a la librería creada modelMapper en la clase principal del proyecto de Spring
    //Convertir una entidad Comentario a un DTO
    private ComentarioDTO mapearDTO(Comentario comentario){
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
        return comentarioDTO;
        //Evitar los atributos de la clase Comentario
        /*ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setNombre(comentario.getNombre());
        comentarioDTO.setEmail(comentario.getEmail());
        comentarioDTO.setCuerpo(comentario.getCuerpo());
        return comentarioDTO;*/
    }
    //Convertir un DTO a  una entidad, mapear de entidad a DTO
    private Comentario mapearEntidad(ComentarioDTO comentarioDTO){
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        return comentario;
       /* Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo());
        return comentario;*/
    }



}
