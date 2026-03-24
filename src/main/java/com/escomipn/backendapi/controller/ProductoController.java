package com.escomipn.backendapi.controller;

import com.escomipn.backendapi.model.Producto;
import com.escomipn.backendapi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos") // Esta es la URL base
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // 1. LEER TODOS (GET)
    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    // 2. LEER UNO SOLO (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR (POST)
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // 4. ACTUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto) {
        Optional<Producto> productoOpcional = productoRepository.findById(id);

        if (productoOpcional.isPresent()) {
            Producto producto = productoOpcional.get();
            producto.setNombre(detallesProducto.getNombre());
            producto.setPrecio(detallesProducto.getPrecio());
            producto.setStock(detallesProducto.getStock());
            return ResponseEntity.ok(productoRepository.save(producto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. BORRAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Devuelve un 204 (Éxito sin contenido)
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}