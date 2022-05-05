package com.clinicaOdontologica.app.service;


import com.clinicaOdontologica.app.entities.AppUser;
import com.clinicaOdontologica.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByEmail(username);
        if(user.isPresent()){
            return user.get();
        }
        throw new UsernameNotFoundException("El email ingresado no existe como usuario registrado.");
    }

}
