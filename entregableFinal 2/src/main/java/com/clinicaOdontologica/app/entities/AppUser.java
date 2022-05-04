package com.clinicaOdontologica.app.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {
    @Id
    @SequenceGenerator (name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "usuario_sequence")
    private Long id;
    @Column
    private String nombre;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUsuarioRoles appUsuarioRoles;

    public AppUser(String nombre, String username, String email, String password, AppUsuarioRoles appUsuarioRoles) {
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.password = password;
        this.appUsuarioRoles = appUsuarioRoles;
    }

    //metodos de la interfaz UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUsuarioRoles.name());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
