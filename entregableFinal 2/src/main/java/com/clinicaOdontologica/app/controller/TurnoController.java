package com.clinicaOdontologica.app.controller;

import com.clinicaOdontologica.app.entities.Odontologo;
import com.clinicaOdontologica.app.entities.Paciente;
import com.clinicaOdontologica.app.entities.Turno;
import com.clinicaOdontologica.app.exceptions.BadRequestException;
import com.clinicaOdontologica.app.exceptions.ResourceNotFoundException;
import com.clinicaOdontologica.app.service.OdontologoService;
import com.clinicaOdontologica.app.service.PacienteService;
import com.clinicaOdontologica.app.service.TurnoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;

    Logger logger = LogManager.getLogger(TurnoController.class);

    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos() throws ResourceNotFoundException {
        logger.info("Recuperando todos los turnos");
        return ResponseEntity.ok(turnoService.listarTurnos());
    }
    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) throws ResourceNotFoundException, BadRequestException {
        ResponseEntity<Turno> respuesta;
        //preguntar si es un paciente correcto y un odontologo correcto
        Optional<Paciente> pacienteBus = pacienteService.buscar(turno.getPaciente().getId());
        logger.info("Buscando paciente con id: " + turno.getPaciente().getId());
        Optional<Odontologo> odontologoBus = odontologoService.buscar(turno.getOdontologo().getId());
        logger.info("Buscando odontologo con id: " + turno.getOdontologo().getId());

        if (pacienteBus.isPresent() && odontologoBus.isPresent()){
            respuesta = ResponseEntity.ok(turnoService.registrarTurno(turno));
        }else{
            respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        logger.info("Registrando turno");
        return respuesta;
    }



    @PutMapping ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) throws ResourceNotFoundException {
        Turno turnoActualizado = turnoService.actualizar(turno);
        if (turnoActualizado != null){
            logger.info("Turno con id: " + turno.getId() + " actualizado");
            return ResponseEntity.ok(turnoActualizado);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Eliminando turno con id: " + id);
        turnoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body("Turno con id=" + id + " eliminado");

    }
}
