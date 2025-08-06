package com.euge.foroHubChallenge.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloIgnoreCaseAndMensajeIgnoreCase(String titulo, String mensaje);

    List<Topico> findAllByOrderByFechaCreacionAsc();

    @Query("""
    SELECT t FROM Topico t 
    WHERE t.curso = :curso 
    AND YEAR(t.fechaCreacion) = :anio
    ORDER BY t.fechaCreacion ASC
""")
    Page<Topico> findByCursoAndAnio(@Param("curso") Curso curso, @Param("anio") int anio, Pageable pageable);

    @Query("""
    SELECT COUNT(t) > 0 FROM Topico t
    WHERE t.curso = :curso
    AND YEAR(t.fechaCreacion) = :anio
""")
    boolean existsByCursoAndAnio(@Param("curso") Curso curso, @Param("anio") int anio);
}
