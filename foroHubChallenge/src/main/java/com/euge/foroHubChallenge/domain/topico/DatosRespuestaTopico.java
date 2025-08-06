package com.euge.foroHubChallenge.domain.topico;

import com.euge.foroHubChallenge.domain.respuestas.DatosRespuesta;

import java.time.LocalDateTime;
import java.util.List;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Curso curso,
        List<DatosRespuesta> respuestas,
        DatosUsuario autor
) {
    public static DatosRespuestaTopico desdeEntidadSinRespuestas(Topico topico) {
        return new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getCurso(),
                List.of(),
                DatosUsuario.desdeEntidad(topico.getAutor())
        );
    }

    public static DatosRespuestaTopico desdeEntidadConRespuestas(Topico topico) {
        List<DatosRespuesta> respuestasDTO = topico.getRespuestas().stream()
                .map(respuesta -> new DatosRespuesta(
                        respuesta.getId(),
                        respuesta.getMensaje(),
                        DatosUsuario.desdeEntidad(respuesta.getAutor())
                ))
                .toList();

        return new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getCurso(),
                respuestasDTO,
                DatosUsuario.desdeEntidad(topico.getAutor())
        );
    }


}