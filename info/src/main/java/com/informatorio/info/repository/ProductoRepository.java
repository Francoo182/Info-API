package com.informatorio.info.repository;

import com.informatorio.info.entity.Categoria;
import com.informatorio.info.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto getById(Long id);
    List<Producto> findByNombreStartingWith(String nombre);
    List<Producto> findByCategoria(Categoria categoria);
    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByPublicadoFalse();
    List<Producto> findByPublicadoTrue();
}