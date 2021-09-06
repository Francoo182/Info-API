package com.informatorio.info.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.informatorio.info.service.UsuarioService;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false,precision = 2, scale = 0)
    private Double precio_unitario;

    @Column(unique = true)
    @NotBlank(message = "Debe ingresar el codigo del producto.")
    private String codigo_inventario;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Categoria categoria;

    @Column(nullable = false,updatable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha_creacion = UsuarioService.creacion();

    @Column(nullable = false)
    private Boolean publicado;

    @Column(nullable = false)
    private String contenido;

    //Getters
    public String getCodigo_inventario() {return codigo_inventario;}
    public Date getFecha_creacion() {return fecha_creacion;}
    public String getDescripcion() {return descripcion;}
    public String getNombre() {return nombre;}
    public Double getPrecio_unitario() {return precio_unitario;}
    public Long getId() {return id;}
    public Categoria getCategoria() {return categoria;}
    public Boolean getPublicado() {return publicado;}
    public String getContenido() {return contenido;}


    //Setters
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    public void setPublicado(Boolean publicado) { this.publicado = publicado;}
    public void setContenido(String contenido){ this.contenido = contenido;}
    public void setCodigo_inventario(String cod) { this.codigo_inventario = cod;}

}