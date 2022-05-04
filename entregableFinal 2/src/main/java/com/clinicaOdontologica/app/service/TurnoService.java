package com.clinicaOdontologica.app.service;

import com.clinicaOdontologica.app.entities.Turno;
import com.clinicaOdontologica.app.exceptions.BadRequestException;
import com.clinicaOdontologica.app.exceptions.ResourceNotFoundException;
import com.clinicaOdontologica.app.repository.TurnoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    TurnoRepository repository;

    Logger logger = LogManager.getLogger(TurnoService.class);

    public Turno registrarTurno(Turno turno) throws BadRequestException {
        Optional<Turno> nuevoTurno = repository.findByFechaAndHora(turno.getFecha(), turno.getHora());
        if (nuevoTurno.isPresent())
            throw new BadRequestException("Ya existe un turno en esa fecha y hora");
        else{
            logger.info("Turno registrado");
            return repository.save(turno);
        }
    }

    public List<Turno> listarTurnos() throws ResourceNotFoundException {
        List<Turno> turnos = repository.findAll();
        if (turnos.isEmpty())
            throw new ResourceNotFoundException("No hay turnos registrados");
        else{
            logger.info("Lista de turnos obtenida");
            return turnos;
        }
    }

    //todo :modelo profe
    public void eliminar (Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado= buscar(id);
        if (turnoBuscado.isPresent())
            repository.deleteById(id);
        else
            throw new ResourceNotFoundException("No existe el turno con id: " + id + ", no se pudo borrar");
    }

    public Turno actualizar(Turno turno) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado=buscar(turno.getId());
        if (turnoBuscado.isPresent()) {
            logger.info("Turno actualizado");
            return repository.save(turno);
        }
        else
            return null;
    }

    public Optional<Turno> buscar(Long id)throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado=repository.findById(id);
        if (turnoBuscado.isPresent()) {
            logger.info("Turno encontrado");
            return turnoBuscado;
        }
        else
            throw new ResourceNotFoundException("No existe el turno con id: " + id);

    }
}
