package com.clinicaOdontologica.app.service;


import com.clinicaOdontologica.app.entities.*;
import com.clinicaOdontologica.app.repository.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataLoader implements ApplicationRunner {

    Logger logger = LogManager.getLogger(DataLoader.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    DomicilioRepository domicilioRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    OdontologoRepository odontologoRepository;
    @Autowired
    TurnoRepository turnoRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("user");
        String password2 = passwordEncoder.encode("admin");
        try {
            Paciente paciente1 = pacienteRepository.save(new Paciente("Natalia", "Gonzalez", 654673, LocalDate.of(1996, 12, 12), "natalia@mail.com", new Domicilio("Maipu", 123,"Rosario","Santa Fe")));
            Paciente paciente2 = pacienteRepository.save(new Paciente("Juan", "Perez", 654673, LocalDate.of(1996, 12, 12),"juan@email.com", new Domicilio("Entre Rios", 456,"Rosario","Santa Fe")));
            logger.info("Pacientes cargados");

            Odontologo odontologo1 = odontologoRepository.save(new Odontologo(654673,"Juan", "Perez"));
            Odontologo odontologo2 = odontologoRepository.save(new Odontologo(654674,"Natalia", "Gonzalez"));
            logger.info("Odontologos cargados");

            turnoRepository.save(new Turno(LocalDate.of(2022, 12, 12), LocalTime.of(12, 30), paciente1, odontologo1));
            turnoRepository.save(new Turno(LocalDate.of(2022, 12, 12), LocalTime.of(13, 30), paciente2, odontologo2));
            logger.info("Turnos cargados");

            userRepository.save(new AppUser("Natalia", "user", "user@email.com", password, AppUsuarioRoles.ROLE_USER));
            logger.info("User created. email: 'user@email.com', password: 'user'");
            userRepository.save(new AppUser("Natalia", "admin" ,"admin@email.com", password2, AppUsuarioRoles.ROLE_ADMIN));
            logger.info("Admin created. email: 'admin@email.com' password: 'admin'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
