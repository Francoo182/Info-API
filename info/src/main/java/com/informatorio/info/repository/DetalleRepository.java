package com.informatorio.info.repository;

import com.informatorio.info.entity.Detalle;
import com.informatorio.info.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRepository extends JpaRepository<Detalle, Long> {
    Detalle getById(Long id);
    Boolean existsByProducto(Producto producto);
}