package com.clinicaOdontologica.app.controller;

import com.clinicaOdontologica.app.entities.Odontologo;
import com.clinicaOdontologica.app.exceptions.BadRequestException;
import com.clinicaOdontologica.app.exceptions.ResourceNotFoundException;
import com.clinicaOdontologica.app.service.OdontologoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    OdontologoService service;

    private static final Logger logger = LogManager.getLogger(OdontologoController.class);

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        logger.info("Registrando odontologo: " + odontologo.getNombre());
        return ResponseEntity.ok(service.registrarOdontologo(odontologo));
    }

    @GetMapping
    public List<Odontologo> buscarOdontologos() throws BadRequestException {
        logger.info("Buscando todos los odontologos");
        return service.buscarTodos();
    }

    @GetMapping("/matricula={matricula}")
    public ResponseEntity<Odontologo> buscarOdontologoPorMatricula(@PathVariable int matricula) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = service.buscarPorMatricula(matricula);
        logger.info("Buscando odontologo con matricula: " + matricula);
        return ResponseEntity.ok(odontologoBuscado.get());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = service.buscar(id);
        logger.info("Buscando odontologo con id: " + id);
        return ResponseEntity.ok(odontologoBuscado.get());
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        logger.info("Actualizando odontologo con id: " + odontologo.getId());
        return ResponseEntity.ok(service.actualizar(odontologo));
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        service.eliminarOdontologo(id);
        logger.info("Eliminando odontologo con id: " + id);
        return ResponseEntity.ok("Odontologo eliminado");
    }
}