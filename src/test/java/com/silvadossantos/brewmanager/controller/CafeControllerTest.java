package com.silvadossantos.brewmanager.controller;

import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.service.CafeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CafeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CafeService cafeService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("GET / deve retornar vista index")
    void deveRetornarVistaIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("GET /cafes deve retornar vista de lista com cafés no modelo")
    void deveRetornarVistaDeListaDeCafes() throws Exception {
        when(cafeService.findAll()).thenReturn(List.of(
                Cafe.builder().id(1L).nome("Test Coffee").build()
        ));

        mockMvc.perform(get("/cafes"))
                .andExpect(status().isOk())
                .andExpect(view().name("lista-cafes"))
                .andExpect(model().attributeExists("cafes"));
    }

    @Test
    @DisplayName("GET /cafes/novo deve retornar formulário com café vazio")
    void deveRetornarFormularioDeNovoCafe() throws Exception {
        mockMvc.perform(get("/cafes/novo"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-cafe"))
                .andExpect(model().attributeExists("cafe"));
    }

    @Test
    @DisplayName("POST /cafes deve salvar café e redirecionar")
    void deveSalvarERedirecionar() throws Exception {
        when(cafeService.save(any())).thenReturn(Cafe.builder().id(1L).nome("New Coffee").build());

        mockMvc.perform(post("/cafes")
                        .param("nome", "New Coffee")
                        .param("marcaTorrefacao", "Torra Forte")
                        .param("origem", "Brasil")
                        .param("nivelTorra", "Médio"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cafes"));
    }

    @Test
    @DisplayName("GET /cafes/{id}/editar deve retornar formulário com café existente")
    void deveRetornarFormularioDeEdicao() throws Exception {
        when(cafeService.findById(1L)).thenReturn(
                Cafe.builder().id(1L).nome("Edit Coffee").build()
        );

        mockMvc.perform(get("/cafes/1/editar"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-cafe"))
                .andExpect(model().attributeExists("cafe"));
    }

    @Test
    @DisplayName("POST /cafes/{id} deve atualizar café e redirecionar")
    void deveAtualizarERedirecionar() throws Exception {
        when(cafeService.save(any())).thenReturn(Cafe.builder().id(1L).nome("Updated Coffee").build());

        mockMvc.perform(post("/cafes/1")
                        .param("nome", "Updated Coffee"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cafes"));
    }

    @Test
    @DisplayName("GET /cafes/{id}/excluir deve excluir café e redirecionar")
    void deveExcluirERedirecionar() throws Exception {
        doNothing().when(cafeService).deleteById(1L);

        mockMvc.perform(get("/cafes/1/excluir"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cafes"));
    }
}
