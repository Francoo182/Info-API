package com.informatorio.info.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.informatorio.info.service.UsuarioService;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "usuarios")
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = true)
    private String nombre;

    @Column(updatable = true)
    private String apellido;

    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion = UsuarioService.creacion();

   /* @JoinColumn(name="direccion_id", referencedColumnName = "id")*/
    //@JsonIgnoreProperties("usuario")
    @Column(updatable = true)
    private String direccion;

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL, orphanRemoval = true)
    /*@JsonIgnoreProperties("usuario")*/
    private List<Carrito> carritos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonIgnoreProperties("usuario")
    private List<Orden> ordenes = new ArrayList<>();


    @Email(regexp = ".+@.+\\..+",message = "Provea un email valido")
    private String email;
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private Boolean activo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public List<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}

