package com.clinicaOdontologica.app.repository;

import com.clinicaOdontologica.app.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional( readOnly = true ) // No se puede hacer una transaccion en el repositorio
public interface UserRepository extends JpaRepository<AppUser,Long>
{
    Optional<AppUser> findByEmail(String email);
}
