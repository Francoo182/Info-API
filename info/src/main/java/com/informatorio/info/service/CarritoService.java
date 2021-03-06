package com.informatorio.info.service;

import com.informatorio.info.entity.Carrito;
import com.informatorio.info.entity.Detalle;
import com.informatorio.info.entity.Producto;
import com.informatorio.info.entity.Usuario;
import com.informatorio.info.repository.CarritoRepository;
import com.informatorio.info.repository.DetalleRepository;
import com.informatorio.info.repository.ProductoRepository;
import com.informatorio.info.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.informatorio.info.service.DetalleService.generarDetalle;
import static com.informatorio.info.service.DetalleService.tratarExistenciaProductoEnCarrito;

@Service
public class CarritoService {
    @Autowired
    private static CarritoRepository carritoRepository;

    @Autowired
    private static UsuarioRepository usuarioRepository;

    @Autowired
    private static ProductoRepository productoRepository;

    @Autowired
    private static DetalleRepository detalleRepository;

    public static Date creacion(){
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    };

    public static Boolean nuevo_carrito(Usuario usuario){
        List<Carrito> carritos_del_user = usuario.getCarritos();
        if (carritos_del_user.size() >=1) {
            Carrito ultimo = carritos_del_user.get(carritos_del_user.size() - 1);
            ultimo.setEstado(false);
        }
        return true;
    }

    public static Object evaluarCerrarCarrito(Carrito carrito){
        if (carrito.getDetalle().size()>=1) {
            carrito.setEstado(false);
            carritoRepository.save(carrito);
            return new ResponseEntity<> (carrito.getDetalle(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Su carrito no tiene productos, no se puede cerrar",HttpStatus.CONFLICT);
        }
    }

    public static void hacerCarritoComprado(Carrito carrito){
        carrito.setEstado(false);
        carritoRepository.save(carrito);
    }

    public static Object evaluarAnadirProducto(Carrito carrito, Long id_producto){
        if (carrito.getEstado()) {
            Producto producto = productoRepository.findById(id_producto).orElse(null);
            if (producto != null) {
                //Detalle detalle = generarDetalle(producto, carrito);
                Detalle detalle = new Detalle();
                detalle.setProducto(producto);
                detalle.setCarrito(carrito);
                tratarExistenciaProductoEnCarrito(carrito, producto, detalle);
                return detalle;
            }else{
                return new ResponseEntity<> ("No existe el producto con el id ingresado",HttpStatus.CONFLICT);
            }
        }else{
            return new ResponseEntity<> ("No se puede a??adir, el carrito esta cerrado",HttpStatus.CONFLICT);
        }
    }

    public static Detalle evaluarDecrementarProducto(Carrito carrito, Long id_producto){
        if (carrito.getEstado()) {
            Producto producto = productoRepository.getById(id_producto);
            List<Detalle> detalles_del_carrito = carrito.getDetalle();
            for (Detalle d : detalles_del_carrito) {
                if (d.getProducto().getId().equals(producto.getId())) {
                    if (d.getCantidad() == 1) {
                        carrito.removeDetalle(d);
                        detalleRepository.save(d);
                        break;
                    } else {
                        d.decCantidad();
                        return detalleRepository.save(d);
                    }
                }
            }
        }
        return null;
    }

    public static Detalle evaluarIncrementarProducto(Carrito carrito, Long id_producto){
        if (carrito.getEstado()) {
            Producto producto = productoRepository.getById(id_producto);
            List<Detalle> detalles_del_carrito = carrito.getDetalle();
            for (Detalle d : detalles_del_carrito) {
                if (d.getProducto().getId().equals(producto.getId())) {
                    d.incCantidad();
                    return detalleRepository.save(d);
                }
            }
        }
        return null;
    }

    public static Detalle tratarSacarProducto(Carrito carrito, Long id_producto){
        if (carrito.getEstado()) {
            Producto producto = productoRepository.getById(id_producto);
            List<Detalle> detalles_del_carrito = carrito.getDetalle();
            for (Detalle d : detalles_del_carrito) {
                if (d.getProducto().getId().equals(producto.getId())) {
                    carrito.removeDetalle(d);
                    detalleRepository.delete(d);
                    return d;
                }
            }
        }
        return null;
    }
}