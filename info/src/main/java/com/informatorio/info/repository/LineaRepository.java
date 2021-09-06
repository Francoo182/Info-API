package com.informatorio.info.repository;

import com.informatorio.info.entity.Linea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaRepository extends JpaRepository<Linea, Long> {
    Linea getById(Long id);
    Boolean existsByProductoId(Long producto_id);
}