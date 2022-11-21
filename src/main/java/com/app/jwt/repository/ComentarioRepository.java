package com.app.jwt.repository;

import com.app.jwt.dto.ComentarioDTO;
import com.app.jwt.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    public List<Comentario> findByPublicacionId(long publicacionId);


}
