package com.informatorio.info.repository;


import com.informatorio.info.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario getById(Long id);
    List<Usuario> getByDireccion(String ciudad);
    List<Usuario> getByFechaCreacionAfter(Date fecha);
    List<Usuario> getByFechaCreacion(Date fecha);
    Boolean existsByEmail(String email);
}
