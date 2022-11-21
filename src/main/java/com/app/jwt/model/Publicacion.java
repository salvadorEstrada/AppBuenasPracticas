package com.app.jwt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="publicaciones",uniqueConstraints ={@UniqueConstraint(columnNames = {"titulo"})})
public class Publicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="titulo", nullable = false)
    private String titulo;
    @Column(name="descripcion", nullable = false)
    private String descripcion;
    @Column(name="contenido", nullable = false)
    private String contenido;
    //orfanRemoval, cuando se elimine una publicacion, de deben eliminar los comentarios asociados
    @JsonBackReference//evita un conjunto de errores cuando se trabaja con DTO'S, tambien considerar la anotacion @transaccional en serv icio implementacion de esta clase
    //Resuelve problemas de recursión infinita por la exitencia de referencias bidireccionales,ignora la seralización
    @OneToMany(mappedBy ="publicacion",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comentario> comentarios = new HashSet<>();

    public Publicacion(){}

    public Publicacion(Long id, String titulo, String descripcion, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
