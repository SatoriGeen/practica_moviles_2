package com.escomipn.backendapi.repository;

import com.escomipn.backendapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring hace la magia de buscar por correo solo con nombrar bien la función
    Optional<Usuario> findByEmail(String email);
}