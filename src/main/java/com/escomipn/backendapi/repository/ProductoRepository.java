package com.escomipn.backendapi.repository;

import com.escomipn.backendapi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí puedes agregar búsquedas personalizadas después, por ahora lo básico ya está incluido.
}