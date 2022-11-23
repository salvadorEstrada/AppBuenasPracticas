package com.app.jwt.controller;

import com.app.jwt.dto.PublicacionDTO;
import com.app.jwt.dto.PublicacionRespuesta;
import com.app.jwt.service.PublicacionServicio;
import com.app.jwt.util.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
    @Autowired
    private PublicacionServicio  publicacionServicio;

    @GetMapping
    public PublicacionRespuesta listarPublicaciones(
            @RequestParam(value="pageNo", defaultValue = AppConstantes.NUM_DE_PAGINA_POR_DEFECTO,required = false) int numPagina,
            @RequestParam(value="pageSize",defaultValue = AppConstantes.TAM_DE_PAGINA_POR_DEFECTO, required = false) int tamPagina,
            @RequestParam(value="sortBy",defaultValue =AppConstantes.ORDENAR_ID_POR_DEFECTO,required = false) String ordenarPor,
            @RequestParam(value="sortDir",defaultValue =AppConstantes.ORDENAR_POR_DEFECTO,required = false) String sortDir) {
        return publicacionServicio.obtenerTodasLasPublicaciones(numPagina, tamPagina, ordenarPor, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable long id){
        return   ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorID(id));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar") //@Valida  para validar los valores de inserción o actualizacion
    public ResponseEntity<PublicacionDTO> guradaPublicaciones(@Valid @RequestBody PublicacionDTO publicacionDTO){
       return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO,@PathVariable long id){
        PublicacionDTO publicacionRespuesta=publicacionServicio.actualizarPublicacion(publicacionDTO,id);
        return  new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable long id){
        publicacionServicio.elimnarPublicacion(id);
        return new ResponseEntity<>("Publicación eliminada exitosamente", HttpStatus.OK);
    }

}
