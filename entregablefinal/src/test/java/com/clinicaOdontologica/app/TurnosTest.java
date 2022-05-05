package com.clinicaOdontologica.app;

import com.clinicaOdontologica.app.entities.Domicilio;
import com.clinicaOdontologica.app.entities.Odontologo;
import com.clinicaOdontologica.app.entities.Paciente;
import com.clinicaOdontologica.app.entities.Turno;
import com.clinicaOdontologica.app.service.DomicilioService;
import com.clinicaOdontologica.app.service.OdontologoService;
import com.clinicaOdontologica.app.service.PacienteService;
import com.clinicaOdontologica.app.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnosTest {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cargar() throws Exception {
        Turno turno = new Turno(LocalDate.of(2022,11,21),LocalTime.of(12,30), pacienteService.buscar(1L).get(), odontologoService.buscar(1L).get());

        String respuesta = "{\"id\":3,\"fecha\":\"2022-11-21\",\"hora\":\"12:30\",\"paciente\":{\"id\":1,\"nombre\":\"Natalia\",\"apellido\":\"Gonzalez\",\"fechaIngreso\":\"1996-12-12\",\"email\":\"natalia@mail.com\",\"domicilio\":{\"id\":1,\"calle\":\"Maipu\",\"numero\":123,\"localidad\":\"Rosario\",\"provincia\":\"Santa Fe\"},\"dni\":654673},\"odontologo\":{\"id\":1,\"matricula\":654673,\"nombre\":\"Juan\",\"apellido\":\"Perez\"}}";

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String pacienteJSON = writer.writeValueAsString(turno);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(pacienteJSON)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }

    @Test
    public void listar () throws Exception {
        //cargarDatosenDB();
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }



    @Test
    public void actualizar() throws Exception {
        Turno turno = new Turno(3L,LocalDate.of(2022,12,21),LocalTime.of(12,30), pacienteService.buscar(1L).get(), odontologoService.buscar(1L).get());

        String respuesta = "{\"id\":3,\"fecha\":\"2022-12-21\",\"hora\":\"12:30\",\"paciente\":{\"id\":1,\"nombre\":\"Natalia\",\"apellido\":\"Gonzalez\",\"fechaIngreso\":\"1996-12-12\",\"email\":\"natalia@mail.com\",\"domicilio\":{\"id\":1,\"calle\":\"Maipu\",\"numero\":123,\"localidad\":\"Rosario\",\"provincia\":\"Santa Fe\"},\"dni\":654673},\"odontologo\":{\"id\":1,\"matricula\":654673,\"nombre\":\"Juan\",\"apellido\":\"Perez\"}}";

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String pacienteJSON = writer.writeValueAsString(turno);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.put("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }

    @Test
    public void Borrar() throws Exception {

        String respuesta = "Turno con id=3 eliminado";
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/turnos/id={id}","3").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }
}
