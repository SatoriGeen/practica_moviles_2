package com.escomipn.backendapi.controller;

import com.escomipn.backendapi.dto.LoginRequest;
import com.escomipn.backendapi.model.Usuario;
import com.escomipn.backendapi.repository.UsuarioRepository;
import com.escomipn.backendapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // 1. REGISTRO DE USUARIO (Este lo habías borrado por accidente)
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: El correo ya está registrado.");
        }

        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    // 2. INICIO DE SESIÓN (LOGIN) QUE DEVUELVE EL TOKEN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOpcional = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpcional.isPresent()) {
            Usuario usuario = usuarioOpcional.get();
            
            if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                // ¡AQUÍ FABRICAMOS EL TOKEN!
                String token = jwtUtil.generarToken(usuario.getEmail());
                
                // Se lo mandamos a Android (o a Postman) como respuesta
                return ResponseEntity.ok(token); 
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Contraseña incorrecta.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuario no encontrado.");
        }
    }
}