package com.app.jwt.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="comentarios")
public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String nombre;
  private String email;
  private String cuerpo;
  //Aquí no uso cascada ni removal, ya cuando elimine un comentario, no tiene por que eliminarse la publicació
    //A la qewu esá asociado el comentario
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="publicacion_id", nullable = false)
  private Publicacion publicacion;

  public Comentario(){};

    public Comentario(long id, String nombre, String email, String cuerpo, Publicacion publicacion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.cuerpo = cuerpo;
        this.publicacion = publicacion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
