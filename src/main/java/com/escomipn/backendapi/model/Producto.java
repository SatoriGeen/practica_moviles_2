package com.escomipn.backendapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Magia de Lombok: crea los getters, setters y constructores automáticamente
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    @Column(name = "cantidad_stock")
    private Integer stock;
}