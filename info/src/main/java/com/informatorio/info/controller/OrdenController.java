package com.informatorio.info.controller;

import com.informatorio.info.entity.Carrito;
import com.informatorio.info.entity.Detalle;
import com.informatorio.info.entity.Orden;
import com.informatorio.info.entity.Usuario;
import com.informatorio.info.repository.CarritoRepository;
import com.informatorio.info.repository.OrdenRepository;
import com.informatorio.info.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.informatorio.info.entity.Estado.Cancelada;
import static com.informatorio.info.entity.Estado.Confirmada;
import static com.informatorio.info.service.LineaService.crearLinea;
import static com.informatorio.info.service.OrdenService.generarNumeroFactura;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/orden")
    public List<Orden> verOrdenes(){
        return  ordenRepository.findAll();
    }

    @GetMapping(value = "/orden/{id_orden}")
    public Object verOrden(@PathVariable("id_orden") Long id_orden){
        Orden orden = ordenRepository.findById(id_orden).orElse(null);
        if (orden == null){
            return new ResponseEntity<>("No existe una orden con el id indicado", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(orden,HttpStatus.OK);
        }
    }

    @PostMapping(value = "/orden/{id_carrito}")
    public Object crearOrden(@PathVariable("id_carrito") Long id_carrito,@RequestBody Orden orden){
        //return tratarCreacionOrden(orden, id_carrito);
        Carrito carrito = carritoRepository.findById(id_carrito).orElse(null);
        if (carrito == null){
            return new ResponseEntity<>("No existe carrito con el id indicado", HttpStatus.NOT_FOUND);
        }else {
            if (!carrito.getEstado() && (carrito.getDetalle().size() >= 1)) {
                orden.setCarritoId(id_carrito);
                orden.setEstado(Confirmada);
                orden.setUsuario(carrito.getUsuario());
                orden.setObservacion(orden.getObservacion());
                orden.setNumero(generarNumeroFactura(carrito));
                List<Detalle> detalles_del_carrito = carrito.getDetalle();
                for  (Detalle d : detalles_del_carrito) {
                    orden.addLinea(crearLinea(d,orden));
                    ordenRepository.save(orden);
                }
                //hacerCarritoComprado(carrito);
                carritoRepository.save(carrito);
                return new ResponseEntity<>(ordenRepository.save(orden),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("Este carrito ya esta cerrado o esta vacio", HttpStatus.CONFLICT);
            }
        }
    }

    @PutMapping(value = "usuario/{id_usuario}/orden/{id_orden}/close")
    public Object cancelarOrden(@PathVariable("id_orden") Long id_orden, @PathVariable("id_usuario") Long id_usuario){
        Orden orden = ordenRepository.findById(id_orden).orElse(null);
        if (orden == null){
            return new ResponseEntity<>("No existe una orden con el id indicado", HttpStatus.NOT_FOUND);
        }else{
            Usuario usuario = usuarioRepository.findById(id_usuario).orElse(null);
            if (usuario != null) {
                if (orden.getEstado() == Confirmada){
                    orden.setEstado(Cancelada);
                    ordenRepository.save(orden);
                    return new ResponseEntity<>("La orden se cancelo", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Tu orden no es una orden confirmada", HttpStatus.CONFLICT);
                }
            }else{
                return new ResponseEntity<>("No existe usuario con el id ingresado.",NOT_FOUND);
            }
        }
    }

    @GetMapping(value = "/usuario/{id_usuario}/orden")
    public Object obtenerOrdenesDelUsuario(@PathVariable("id_usuario") Long id_usuario){
        Usuario user = usuarioRepository.findById(id_usuario).orElse(null);
        if (user == null){
            return new ResponseEntity<>("El usuario con el id indicado no existe.",HttpStatus.NOT_FOUND);
        }else{
            return ordenRepository.findByUsuario(user);
        }
    }

}