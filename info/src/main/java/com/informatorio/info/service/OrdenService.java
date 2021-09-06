package com.informatorio.info.service;

import com.informatorio.info.entity.*;
import com.informatorio.info.repository.CarritoRepository;
import com.informatorio.info.repository.OrdenRepository;
import com.informatorio.info.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.informatorio.info.entity.Estado.Cancelada;
import static com.informatorio.info.entity.Estado.Confirmada;

import static com.informatorio.info.service.CarritoService.hacerCarritoComprado;
import static com.informatorio.info.service.LineaService.crearLinea;

@Service
public class OrdenService {

    @Autowired
    private static CarritoRepository carritoRepository;

    @Autowired
    private static OrdenRepository ordenRepository;

    @Autowired
    private static UsuarioRepository usuarioRepository;

    public static Long generarNumeroFactura(Carrito carrito) {
        Random random = new Random();
        Long numero = carrito.getUsuario().getId()*carrito.getId()*random.nextInt();
        return numero;
    }

    public static void cargarLineas(Carrito carrito, Orden orden){
        List<Detalle> detalles_del_carrito = carrito.getDetalle();
        for  (Detalle d : detalles_del_carrito) {
            orden.addLinea(crearLinea(d,orden));
            ordenRepository.save(orden);
        }
    }

    public static Object tratarCreacionOrden(Orden orden,Long id_carrito){
        Carrito carrito = carritoRepository.findById(id_carrito).orElse(null);
        if (carrito == null){
            return new ResponseEntity<>("No existe carrito con el id indicado", HttpStatus.NOT_FOUND);
        }else {
            if (carrito.getEstado() && (carrito.getDetalle().size() >= 1)) {
                orden.setCarritoId(id_carrito);
                orden.setEstado(Confirmada);
                orden.setUsuario(carrito.getUsuario());
                orden.setObservacion(orden.getObservacion());
                orden.setNumero(generarNumeroFactura(carrito));
                cargarLineas(carrito, orden);
                hacerCarritoComprado(carrito);
                return new ResponseEntity<>(ordenRepository.save(orden),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("Este carrito ya esta cerrado o esta vacio", HttpStatus.CONFLICT);
            }
        }
    }

    public static Object tratarCancelarOrden(Orden orden, Long id_usuario){
        Usuario usuario = usuarioRepository.getById(id_usuario);
        if (orden.getEstado()==Confirmada){
            orden.setEstado(Cancelada);
            return ordenRepository.save(orden);
        }else{
            return new ResponseEntity<>("Tu orden no es una orden confirmada", HttpStatus.CONFLICT);
        }
    }

}