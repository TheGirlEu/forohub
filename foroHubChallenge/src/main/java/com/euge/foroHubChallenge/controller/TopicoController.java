package com.euge.foroHubChallenge.controller;

import com.euge.foroHubChallenge.domain.topico.*;
import com.euge.foroHubChallenge.domain.usuario.Usuario;
import com.euge.foroHubChallenge.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        Usuario autor = usuarioRepository.findById(datosRegistroTopico.autorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error, el usuario no puede crear topicos, debe estar previamente registrado en el sistema"));

        boolean existeDuplicado = topicoRepository.existsByTituloIgnoreCaseAndMensajeIgnoreCase(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje());
        if (existeDuplicado) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un tópico con ese título y mensaje");
        }

        Topico topico = new Topico(datosRegistroTopico, autor);
        topicoRepository.save(topico);

        return ResponseEntity.status(HttpStatus.CREATED).body(DatosRespuestaTopico.desdeEntidadConRespuestas(topico));
    }

    @GetMapping
    public ResponseEntity<List<DatosRespuestaTopico>> listarTopicos() {
        List<Topico> topicos = topicoRepository.findAll();

        List<DatosRespuestaTopico> respuesta = topicos.stream()
                .map(DatosRespuestaTopico::desdeEntidadConRespuestas)
                .toList();

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<DatosRespuestaTopico>> listarPrimeros10Topicos() {
        List<Topico> primeros10 = topicoRepository.findAllByOrderByFechaCreacionAsc()
                .stream()
                .limit(10)
                .collect(Collectors.toList());

        List<DatosRespuestaTopico> respuesta = primeros10.stream()
                .map(DatosRespuestaTopico::desdeEntidadConRespuestas)
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscarTopico")
    public ResponseEntity<Page<DatosRespuestaTopico>> buscarTopicosPorCursoYAnio(
            @RequestParam Curso curso,
            @RequestParam int anio,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable pageable
    ) {

        boolean existeTopico = topicoRepository.existsByCursoAndAnio(curso, anio);

        if (existeTopico) {
            Page<Topico> topicos = topicoRepository.findByCursoAndAnio(curso, anio, pageable);

            Page<DatosRespuestaTopico> respuesta = topicos.map(DatosRespuestaTopico::desdeEntidadConRespuestas);

            return ResponseEntity.ok(respuesta);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay tópicos para ese curso y año");
        }
    }

    @GetMapping("/detalleTopico/{id}")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopicoPorId(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        return ResponseEntity.ok(DatosRespuestaTopico.desdeEntidadConRespuestas(topico));
    }

    @PutMapping("/actualizarTopico/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos
    ) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El topico que desea actualizar no existe."));

        topico.actualizarDatos(datos);

        return ResponseEntity.ok(DatosRespuestaTopico.desdeEntidadConRespuestas(topico));
    }

    @DeleteMapping("/eliminarTopico/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        topicoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
