package com.informatorio.info.repository;

import com.informatorio.info.entity.Orden;
import com.informatorio.info.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    Orden getById(Long id);
    List<Orden> findByUsuario(Usuario usuario);
    Orden findByCarritoId(Long id_carrito);
    Orden findByLineaProductoId(Long producto_id);
}