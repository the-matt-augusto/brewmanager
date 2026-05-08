package com.silvadossantos.brewmanager.controller;

import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.service.TipoInfusaoService;
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
class TipoInfusaoControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private TipoInfusaoService tipoInfusaoService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("GET /tipos-infusao deve retornar vista de lista com tipos no modelo")
    void deveRetornarVistaDeLista() throws Exception {
        when(tipoInfusaoService.findAll()).thenReturn(List.of(
                TipoInfusao.builder().id(1L).nome("V60").build()
        ));

        mockMvc.perform(get("/tipos-infusao"))
                .andExpect(status().isOk())
                .andExpect(view().name("lista-tipos-infusao"))
                .andExpect(model().attributeExists("tipos"));
    }

    @Test
    @DisplayName("GET /tipos-infusao/novo deve retornar formulário com TipoInfusao vazio")
    void deveRetornarFormularioDeNovo() throws Exception {
        mockMvc.perform(get("/tipos-infusao/novo"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-tipo-infusao"))
                .andExpect(model().attributeExists("tipoInfusao"));
    }

    @Test
    @DisplayName("POST /tipos-infusao deve salvar e redirecionar")
    void deveSalvarERedirecionar() throws Exception {
        when(tipoInfusaoService.save(any())).thenReturn(
                TipoInfusao.builder().id(1L).nome("V60").build()
        );

        mockMvc.perform(post("/tipos-infusao")
                        .param("nome", "V60")
                        .param("descricao", "Coagem por gotejamento"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tipos-infusao"));
    }

    @Test
    @DisplayName("GET /tipos-infusao/{id}/editar deve retornar formulário com tipo existente")
    void deveRetornarFormularioDeEdicao() throws Exception {
        when(tipoInfusaoService.findById(1L)).thenReturn(
                TipoInfusao.builder().id(1L).nome("V60").descricao("Coagem por gotejamento").build()
        );

        mockMvc.perform(get("/tipos-infusao/1/editar"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-tipo-infusao"))
                .andExpect(model().attributeExists("tipoInfusao"));
    }

    @Test
    @DisplayName("POST /tipos-infusao/{id} deve atualizar e redirecionar")
    void deveAtualizarERedirecionar() throws Exception {
        when(tipoInfusaoService.save(any())).thenReturn(
                TipoInfusao.builder().id(1L).nome("V60 Updated").build()
        );

        mockMvc.perform(post("/tipos-infusao/1")
                        .param("nome", "V60 Updated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tipos-infusao"));
    }

    @Test
    @DisplayName("GET /tipos-infusao/{id}/excluir deve excluir e redirecionar")
    void deveExcluirERedirecionar() throws Exception {
        doNothing().when(tipoInfusaoService).deleteById(1L);

        mockMvc.perform(get("/tipos-infusao/1/excluir"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tipos-infusao"));
    }
}
