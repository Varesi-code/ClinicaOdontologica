package com.clinicaOdontologica.app.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "odontologos")
public class Odontologo {

    @Id
    @SequenceGenerator (name = "odontologo_sequence", sequenceName = "odontologo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "odontologo_sequence")
    private Long id;
    @Column
    private int matricula;
    @Column
    private String nombre;
    @Column
    private String apellido;

    // atributos de relaciones
    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turno> turnos=new HashSet<>();

    public Odontologo(int matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Odontologo(Long id, int matricula, String nombre, String apellido) {
        this.id = id;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
