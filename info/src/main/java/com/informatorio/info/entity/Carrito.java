package com.informatorio.info.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)

    private Boolean estado = true;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDate fecha_creacion;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detalle> detalle = new ArrayList<>();

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @NotBlank
    @Column(nullable = false)
    private String origen;
    @Transient
    private Double costoTotal;

    //Getters
    public Long getId() {return id;}
    public Boolean getEstado() {return estado;}
    public Long getUsuarioId() {return usuario.getId();}

    public Usuario getUsuario() {return usuario;}
    public LocalDate getFecha_creacion() {return fecha_creacion;}
    public List<Detalle> getDetalle() {return detalle;}


    public Double getCostoTotal(){
        Double total = 0.0;
        for ( Detalle d : this.getDetalle()){
            total = d.getSubTotal() + total;
        }
        return total;
    }

    //Setters
    public void addDetalle(Detalle detalle){this.getDetalle().add(detalle);}
    public void removeDetalle(Detalle detalle){
        this.getDetalle().remove(detalle);
    }
    public void setEstado(Boolean estado) {this.estado = estado;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}


}
