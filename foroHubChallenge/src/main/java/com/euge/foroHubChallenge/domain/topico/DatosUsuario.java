package com.euge.foroHubChallenge.domain.topico;

import com.euge.foroHubChallenge.domain.usuario.Usuario;

public record DatosUsuario(
        String nombre,
        String apellido
) {
    public static DatosUsuario desdeEntidad(Usuario usuario) {
        return new DatosUsuario(usuario.getNombre(), usuario.getApellido());
    }
}