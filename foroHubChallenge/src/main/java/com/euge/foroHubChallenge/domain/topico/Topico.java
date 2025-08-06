package com.euge.foroHubChallenge.domain.topico;

import com.euge.foroHubChallenge.domain.respuestas.Respuesta;
import com.euge.foroHubChallenge.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Enumerated(EnumType.STRING)
    private Curso curso;

    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoTopico status = EstadoTopico.NO_SOLUCIONADO;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;

    public Topico(DatosRegistroTopico datos, Usuario autor) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.curso = datos.curso();
        this.autor = autor;
        this.fechaCreacion = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.status = EstadoTopico.NO_SOLUCIONADO;
        this.respuestas = new ArrayList<>();
    }

    public void actualizarDatos(DatosActualizarTopico datos) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.curso = datos.curso();
    }


}