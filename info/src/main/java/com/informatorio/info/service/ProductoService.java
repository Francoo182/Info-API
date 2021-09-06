package com.informatorio.info.service;

import com.informatorio.info.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private static ProductoRepository productoRepository;
}

