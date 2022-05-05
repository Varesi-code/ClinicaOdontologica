package com.clinicaOdontologica.app.repository;

import com.clinicaOdontologica.app.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    Optional<Turno> findByFechaAndHora(LocalDate fecha, LocalTime hora);
}
