package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;
import java.util.Arrays;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenPersonsWhenGetPersonsThenStatus200() throws Exception {

        Person p1 = new Person(1, "login", "password");
        Person p2 = new Person(2, "login2", "password2");
        Mockito.when(personRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        mockMvc.perform(
                get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(p1, p2))));
    }

    @Test
    public void givenIdWhenGetExistingPersonThenStatus200AndPersonReturned() throws Exception {
        Person p1 = new Person(1, "login", "password");
        Mockito.when(personRepository.findById(Mockito.any())).thenReturn(Optional.of(p1));
        mockMvc.perform(get("/person/" + p1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.login").value("login"))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    public void givenIdWhenGetNotExistingPersonThenStatus404() throws Exception {
        Mockito.when(personRepository.findById(Mockito.any())).
                thenReturn(Optional.empty());
        mockMvc.perform(
                get("/person/1"))
                .andExpect(status().isNotFound());
   }

    @Test
    public void whenCreatePersonThenStatus201() throws Exception {
        Person p1 = new Person(1, "login", "password");
        Mockito.when(personRepository.save(Mockito.any())).thenReturn(p1);
        String requestBody = objectMapper.writeValueAsString(p1);
        mockMvc.perform(post("/person/")
                .content(requestBody)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(p1)));
    }

    @Test
    public void givenPersonWhenDeletePersonThenStatus200() throws Exception {
        Person p1 = new Person(1, "login", "password");
        Mockito.when(personRepository.findById(p1.getId())).thenReturn(Optional.of(p1));
        mockMvc.perform(delete("/person/" + p1.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givePersonWhenUpdateThenStatus200andUpdatedReturns() throws Exception {
        Person p1 = new Person(1, "login", "password");
        Person p2 = new Person(1, "login2", "password");
        Mockito.when(personRepository.save(Mockito.any())).thenReturn(p2);
        Mockito.when(personRepository.findById(Mockito.any())).thenReturn(Optional.of(p1));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/person/{id}", 1)
                        .content(objectMapper.writeValueAsString(p2))
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
