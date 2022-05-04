package com.clinicaOdontologica.app.service;
import com.clinicaOdontologica.app.entities.Odontologo;
import com.clinicaOdontologica.app.exceptions.BadRequestException;
import com.clinicaOdontologica.app.exceptions.ResourceNotFoundException;
import com.clinicaOdontologica.app.repository.OdontologoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    OdontologoRepository repository;

    Logger logger = LogManager.getLogger(OdontologoService.class);

    public Odontologo registrarOdontologo(Odontologo odontologo) throws BadRequestException{
        Optional<Odontologo> odontologoBuscado = repository.findByMatricula(odontologo.getMatricula());
        if (odontologoBuscado.isPresent())
            throw new BadRequestException("Ya existe un odontologo con matricula: "+odontologo.getMatricula()+".");
        else
            return repository.save(odontologo);
    }

    public List<Odontologo> buscarTodos() throws BadRequestException{
        List<Odontologo> odontologos= repository.findAll();
        if (odontologos.isEmpty()){
            throw new BadRequestException("No hay odontologos registrados.");
        }else{
            return odontologos;
        }
    }

    public Optional<Odontologo> buscarPorMatricula(int matricula) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= repository.findByMatricula(matricula);
        if (odontologoBuscado.isPresent()){
            logger.info("Odontologo encontrado: "+odontologoBuscado.get().getMatricula());
            return odontologoBuscado;
        } else {
            throw new ResourceNotFoundException("No se puede encontrar el odontologo con matricula: "+matricula+".");
        }
    }

    public Optional<Odontologo> buscar(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= repository.findById(id);
        if (odontologoBuscado.isPresent()){
            logger.info("Se encontro el odontologo con id: "+id);
            return odontologoBuscado;
        }else{
            throw new ResourceNotFoundException("No se puede encontrar el odontologo con id: "+id+", error al buscar");
        }
    }

    public Odontologo actualizar(Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= buscar(odontologo.getId());
        if (odontologoBuscado.isPresent())
            return repository.save(odontologo);
        else
            return null;
    }

    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= buscar(id);
        if (odontologoBuscado.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("No se puede borrar el odontologo con id: "+id+", error al borrar");
        }
    }
}