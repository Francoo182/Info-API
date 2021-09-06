package com.informatorio.info.service;

import com.informatorio.info.entity.Detalle;
import com.informatorio.info.entity.Linea;
import com.informatorio.info.entity.Orden;
import org.springframework.stereotype.Service;

@Service
public class LineaService {

    public static Linea crearLinea(Detalle d, Orden orden){
        Linea l = new Linea();
        l.setProducto_id(d.getProducto().getId());
        l.setCantidad(d.getCantidad());
        l.setPrecio(d.getProducto().getPrecio_unitario());
        l.setOrden(orden);
        return l;
    }
}