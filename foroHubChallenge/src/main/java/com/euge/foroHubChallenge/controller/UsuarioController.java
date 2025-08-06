package com.euge.foroHubChallenge.controller;

import com.euge.foroHubChallenge.domain.usuario.DatosRegistroUsuario;
import com.euge.foroHubChallenge.domain.usuario.DatosRespuestaUsuario;
import com.euge.foroHubChallenge.domain.usuario.Usuario;
import com.euge.foroHubChallenge.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/register")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {

        boolean usuarioExiste = usuarioRepository.existsByEmail(datosRegistroUsuario.email());

        if (usuarioExiste) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ingresado ya se encuentra registrado");
        }

        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario, passwordEncoder));
        DatosRespuestaUsuario respuesta = new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail()
        );

        URI uri = URI.create("/usuarios/" + usuario.getId());

        return ResponseEntity.created(uri).body(respuesta);
    }
}
