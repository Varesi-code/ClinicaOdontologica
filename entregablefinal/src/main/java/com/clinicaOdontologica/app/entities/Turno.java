package com.clinicaOdontologica.app.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="turnos")
public class Turno {

    @Id
    @SequenceGenerator (name = "turno_sequence", sequenceName = "turno_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "turno_sequence")
    private Long id;

    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate fecha;

    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalTime hora;

    // atributos de relacion
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="odontologo_id", nullable = false)
    private Odontologo odontologo;

    public Turno(LocalDate fecha, LocalTime hora, Paciente paciente, Odontologo odontologo) {
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.odontologo = odontologo;
    }

    public Turno(Long id, LocalDate fecha, LocalTime hora, Paciente paciente,Odontologo odontologo) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.odontologo = odontologo;
    }
}
