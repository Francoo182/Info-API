package com.informatorio.info.repository;


import com.informatorio.info.entity.Carrito;
import com.informatorio.info.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Carrito getById(Long id);

    List<Carrito> findByUsuario(Usuario usuario);
}
