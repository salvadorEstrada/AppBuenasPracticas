package com.app.jwt.service;

import com.app.jwt.dto.PublicacionDTO;
import com.app.jwt.dto.PublicacionRespuesta;
import com.app.jwt.exeption.ResourceNotFoundException;
import com.app.jwt.model.Publicacion;
import com.app.jwt.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional //SE CONSIDERA ESTA ANOTACIÖN PARA QUE MUESTRE LOS COMENTARIOS QUE TIENE UNA PUBLICACIÖN
public class PublicacionServicioImpl implements PublicacionServicio{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        //Convertir de DTO a entidad
        Publicacion  publicacion = mapearEntidad(publicacionDTO);
        Publicacion nuevaPublicacion=publicacionRepository.save(publicacion);
        //Convertimos de Entidad a DTO
        PublicacionDTO  publicacionRespuesta = mapearDTO(nuevaPublicacion);
        return publicacionRespuesta;
    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int tamPagina, String ordenarPor,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, tamPagina,sort);
        Page<Publicacion> publicaciones =publicacionRepository.findAll(pageable);
        List<Publicacion> listaDePublicaciones = publicaciones.getContent();
        List<PublicacionDTO>  contenido=listaDePublicaciones.stream().map(publicacion ->mapearDTO(publicacion)).collect(Collectors.toList());

        PublicacionRespuesta publicacionRespuesta= new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setTamPagina(publicaciones.getSize());
        publicacionRespuesta.setTotElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }


    @Override
    public PublicacionDTO obtenerPublicacionPorID(long id) {
        Publicacion publicacion  = publicacionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("publicacion","id",id));
        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
        Publicacion publicacion  = publicacionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("publicacion","id",id));
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());
        Publicacion publicioActualizada = publicacionRepository.save(publicacion);
        return mapearDTO(publicioActualizada);
    }

    @Override
    public void elimnarPublicacion(@PathVariable long id) {
        Publicacion publicacion  = publicacionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("publicacion","id",id));
       publicacionRepository.delete(publicacion);

    }

    //###################SE CREA UNA LIBRERÏA EN LA CLASE PRINCIPAl DE PROYECTO#############
    //Convierte Entidad a DTO,se crea un libreria en la clase principal para hacer más claro elcódigo
    private PublicacionDTO mapearDTO(Publicacion publicacion){
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
        return publicacionDTO;
        /*PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setTitulo(publicacion.getTitulo());
        publicacionDTO.setDescripcion(publicacion.getDescripcion());
        publicacionDTO.setContenido(publicacion.getContenido());
        return publicacionDTO;*/
    }

    //Convierte de DTO a Entidad, Mapear entidad,se crea un libreria en la clase principal para hacer más claro elcódigo
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){
        Publicacion publicacion= modelMapper.map(publicacionDTO, Publicacion.class);
        return publicacion;
        /*Publicacion publicacion = new Publicacion();
        publicacion.setId(publicacionDTO.getId());
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());
        return publicacion;*/
    }
    //###############################################################################################

}
