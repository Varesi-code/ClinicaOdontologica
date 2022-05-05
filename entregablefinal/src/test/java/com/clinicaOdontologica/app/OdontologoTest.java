package com.clinicaOdontologica.app;

import com.clinicaOdontologica.app.entities.Odontologo;
import com.clinicaOdontologica.app.service.OdontologoService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OdontologoTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void cargar() throws Exception {
        Odontologo odontologo = new Odontologo(123,"carmelo","sanchez");
        String respuesta = "{\"id\":3,\"matricula\":123,\"nombre\":\"carmelo\",\"apellido\":\"sanchez\"}";

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String odontologoJSON = writer.writeValueAsString(odontologo);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }

    @Test
    public void listarOdontologos() throws Exception {
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        System.out.println(resultado);
    }
    @Test
    public void actualizar() throws Exception {
        Odontologo odontologo = new Odontologo(3L,5787,"pedro","perez");
        String respuesta = "{\"id\":3,\"matricula\":5787,\"nombre\":\"pedro\",\"apellido\":\"perez\"}";

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String odontologoJSON = writer.writeValueAsString(odontologo);

        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.put("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(resultado.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }

    @Test
    public void Borrar() throws Exception {

        String respuesta = "Odontologo eliminado";
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/odontologos/id={id}","3").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(respuesta,resultado.getResponse().getContentAsString());
    }


}