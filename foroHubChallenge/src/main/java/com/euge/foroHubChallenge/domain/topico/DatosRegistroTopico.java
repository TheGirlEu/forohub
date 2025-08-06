package com.euge.foroHubChallenge.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,

        @NotNull(message = "El curso es obligatorio")
        Curso curso,

        @NotNull(message = "El autor es obligatorio")
        Long autorId
) {
}
