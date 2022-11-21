package com.app.jwt.controller;

import com.app.jwt.dto.ComentarioDTO;
import com.app.jwt.model.Comentario;
import com.app.jwt.service.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ComentarioController {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacionId(@PathVariable(value="publicacionId") Long  publicacionId) {
       return comentarioServicio.obtenerComentariosPorPublicacionId(publicacionId);
    }
    //###################OBTIENE COMENTARIOS POR ID ############################
    @GetMapping("publicaciones/{publicacionId}/comentarios/{id}")
     public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(value="publicacionId") Long publicacionId, @PathVariable(value="id") Long id){
        ComentarioDTO comentario=comentarioServicio.obtenerComentarioPorId(publicacionId,id);
        return  new ResponseEntity<>(comentario,HttpStatus.OK);
    }
    //###################CREA COMENTARIOS  ############################
    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    //La anotación @Valid debe de estar precedida de @RequestBody para que funciones
    public ResponseEntity<ComentarioDTO> guardaComentario(@PathVariable long  publicacionId, @Valid @RequestBody ComentarioDTO comentarioDTO){
     return  new ResponseEntity(comentarioServicio.crearComentario(publicacionId,comentarioDTO), HttpStatus.CREATED);
    }
    //###################ACTUALIZA COMENTARIOS POR ID ############################
    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> actualizarComentarios(@PathVariable(value="publicacionId")Long publicacionId, @PathVariable(value="comentarioId")Long comentarioId,@Valid @RequestBody ComentarioDTO comentarioDTO){
         ComentarioDTO comentarioActualizado =  comentarioServicio.actualizarComentario(publicacionId,comentarioId,comentarioDTO);
        return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
    }
    //###################ELIMINA COMENTARIOS MEDIANTE EL ID DE PUBLICACIÖN Y COMENTARIO############################
    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(value="publicacionId")Long publicacionId, @PathVariable(value="comentarioId")Long comentarioId){
        comentarioServicio.eliminarComentario(publicacionId, comentarioId);
    return new ResponseEntity<>("Comentario eliminado con exito!",HttpStatus.OK);
    }
}
