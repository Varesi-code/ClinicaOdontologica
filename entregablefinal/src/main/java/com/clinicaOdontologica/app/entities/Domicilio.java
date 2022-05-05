package com.clinicaOdontologica.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "domicilios")
public class Domicilio {
    @Id
    @SequenceGenerator (name = "domicilio_sequence", sequenceName = "domicilio_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "domicilio_sequence")
    private Long id;

    @Column
    private String calle;

    @Column
    private Integer numero;

    @Column
    private String localidad;

    @Column
    private String provincia;

    //Relacion con la clase persona, la foreign key(@joincolumn) esta en persona y no en domicilio
    //donde se mapea en la base de datos la relacion
    //voy a poder conocer al paciente a traves del domicilio
    @OneToOne(mappedBy = "domicilio")
    @JsonIgnore
    private Paciente paciente;

    public Domicilio(String calle, Integer numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }
}
