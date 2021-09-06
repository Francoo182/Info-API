package com.informatorio.info.repository;


import com.informatorio.info.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long>{
    Direccion getById(Long id);
}