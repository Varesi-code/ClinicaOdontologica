package com.clinicaOdontologica.app.controller;

import com.clinicaOdontologica.app.entities.Paciente;
import com.clinicaOdontologica.app.exceptions.BadRequestException;
import com.clinicaOdontologica.app.exceptions.InternalServerException;
import com.clinicaOdontologica.app.exceptions.ResourceNotFoundException;
import com.clinicaOdontologica.app.service.PacienteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    Logger logger = LogManager.getLogger(PacienteController.class);

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        logger.info("Registrando paciente");
        return ResponseEntity.ok(pacienteService.guardar(paciente));
    }

    @GetMapping
    public List<Paciente> buscarPacientes() throws ResourceNotFoundException {
        logger.info("Buscando todos los pacientes");
        return pacienteService.buscarTodos();
    }

    @GetMapping("/mail={email}")
    public ResponseEntity<Paciente> buscarPacientePorEmail(@PathVariable String email) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPorEmail(email);
        return ResponseEntity.ok(pacienteBuscado.get());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=pacienteService.buscar(id);
        logger.info("Buscando paciente con id: "+id);
        return ResponseEntity.ok(pacienteBuscado.get());
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        Paciente pacienteActualizado=pacienteService.actualizar(paciente);
        logger.info("Actualizando paciente");
        return ResponseEntity.ok(pacienteActualizado);
    }


    @DeleteMapping("/id={id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException, InternalServerException {
        pacienteService.eliminar(id);
        return ResponseEntity.ok("Paciente eliminado con id: "+id);
    }
}
