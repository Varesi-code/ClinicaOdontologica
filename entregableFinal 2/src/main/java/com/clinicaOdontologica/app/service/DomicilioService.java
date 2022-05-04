package com.clinicaOdontologica.app.service;

import com.clinicaOdontologica.app.entities.Domicilio;
import com.clinicaOdontologica.app.exceptions.BadRequestException;
import com.clinicaOdontologica.app.exceptions.GlobalExceptions;
import com.clinicaOdontologica.app.exceptions.ResourceNotFoundException;
import com.clinicaOdontologica.app.repository.DomicilioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService {
    @Autowired
    DomicilioRepository repository;

    private static final Logger logger = LogManager.getLogger(DomicilioService.class);

    public Domicilio registrarDomicilio(Domicilio domicilio) throws BadRequestException {
        if (domicilio.getId() != null)
            throw new BadRequestException("El domicilio ya existe");
        else
            return repository.save(domicilio);
    }

    public Optional<Domicilio> buscar(Long id) throws ResourceNotFoundException {
        Optional<Domicilio> domicilio = repository.findById(id);
        if (domicilio.isPresent())
            return domicilio;
        else {
            logger.error("No se encontro el domicilio con id: " + id);
            throw new ResourceNotFoundException("No se encontro el domicilio con id: " + id);
        }
    }

    public List<Domicilio> buscarTodos() throws ResourceNotFoundException {
        List<Domicilio> domicilios = repository.findAll();
        if (domicilios.isEmpty()) {
            logger.error("No se encontraron domicilios");
            throw new ResourceNotFoundException("No se encontraron domicilios");
        }else
            return domicilios;
    }

    public Domicilio actualizar(Domicilio domicilio)  throws ResourceNotFoundException {
        if (buscar(domicilio.getId()).isPresent()) {
            logger.info("Se actualizo el domicilio con id: " + domicilio.getId());
            return repository.save(domicilio);
        } else
            return null;
    }

    public void eliminarDomicilio(Long id) throws ResourceNotFoundException {
        if (buscar(id).isPresent()){
            logger.info("Se elimino el domicilio con id: " + id);
            repository.deleteById(id);
        }else
            throw new ResourceNotFoundException("No se encontro el domicilio con id: " + id);
    }
}
