package com.clinicaOdontologica.app;
import com.clinicaOdontologica.app.entities.Domicilio;
import com.clinicaOdontologica.app.entities.Paciente;
import com.clinicaOdontologica.app.service.PacienteService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PacienteTest {
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void cargar() throws Exception {

        Paciente paciente = new Paciente(3L,"Valentino", "Lapirana", 32456456, LocalDate.of(2020, 12, 12), "valen@mail.com", new Domicilio("Maipu", 13,"Rosario","Santa Fe"));
        String respuesta = "{\"id\":3,\"nombre\":\"Valentino\",\"apellido\":\"Lapirana\",\"fechaIngreso\":\"2020-12-12\",\"email\":\"valen@mail.com\",\"domicilio\":{\"id\":3,\"calle\":\"Maipu\",\"numero\":13,\"localidad\":\"Rosario\",\"provincia\":\"Santa Fe\"},\"dni\":32456456}";

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String pacienteJSON = writer.writeValueAsString(paciente);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(pacienteJSON)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }

    @Test
    public void listar() throws Exception {
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        System.out.println(resultado);
    }
    @Test
    public void actualizar() throws Exception {
        Paciente paciente = new Paciente(3L,"carmelo", "Lapirana", 32456456, LocalDate.of(2020, 12, 12), "valen@mail.com", new Domicilio("Maipu", 13,"Rosario","Santa Fe"));
        String respuesta = "{\"id\":3,\"nombre\":\"carmelo\",\"apellido\":\"Lapirana\",\"fechaIngreso\":\"2020-12-12\",\"email\":\"valen@mail.com\",\"domicilio\":{\"id\":4,\"calle\":\"Maipu\",\"numero\":13,\"localidad\":\"Rosario\",\"provincia\":\"Santa Fe\"},\"dni\":32456456}";

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String pacienteJSON = writer.writeValueAsString(paciente);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.put("/pacientes")
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

        String respuesta = "Paciente eliminado con id: 3";
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/pacientes/id={id}","3").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }
}