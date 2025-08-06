package com.euge.foroHubChallenge.domain.respuestas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCrearRespuesta(
        @NotBlank(message = "El mensaje de la respuesta es obligatorio")
        String mensaje,
        @NotNull
        Long topicoId,
        @NotNull
        Long usuarioId
) {
}
