package com.clinicaOdontologica.app.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="pacientes")
public class Paciente {

    @Id
    @SequenceGenerator (name = "paciente_sequence", sequenceName = "paciente_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "paciente_sequence")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "dni")
    private Integer DNI;

    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name="email")
    private String email;

    // atributos de relaciones

    //para que afecte a la tabla de domicilios(cascade)
    @OneToOne(cascade = CascadeType.ALL)
    //se crea la clave foranea. puedo elegir el nombre
    @JoinColumn(name = "domicilio_id" , referencedColumnName = "id")
    private Domicilio domicilio;

    @OneToMany( mappedBy = "paciente", fetch = FetchType.LAZY)
    //para no generar un bucle infinito
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

    public Paciente(String nombre, String apellido, Integer DNI, LocalDate fechaIngreso, String email, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.fechaIngreso = fechaIngreso;
        this.email = email;
        this.domicilio = domicilio;
    }

    public Paciente(Long id, String nombre, String apellido, Integer DNI, LocalDate fechaIngreso, String email, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.fechaIngreso = fechaIngreso;
        this.email = email;
        this.domicilio = domicilio;
    }
}
