package com.silvadossantos.brewmanager.controller;

import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.model.Receita;
import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.service.CafeService;
import com.silvadossantos.brewmanager.service.ReceitaService;
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
class ReceitaControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ReceitaService receitaService;

    @MockitoBean
    private CafeService cafeService;

    @MockitoBean
    private TipoInfusaoService tipoInfusaoService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("GET /receitas deve retornar vista de lista com receitas no modelo")
    void deveRetornarVistaDeLista() throws Exception {
        when(receitaService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/receitas"))
                .andExpect(status().isOk())
                .andExpect(view().name("lista-receitas"))
                .andExpect(model().attributeExists("receitas"));
    }

    @Test
    @DisplayName("GET /receitas/novo deve retornar formulário com receita, cafés e tipos vazios")
    void deveRetornarFormularioDeNovo() throws Exception {
        when(cafeService.findAll()).thenReturn(List.of());
        when(tipoInfusaoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/receitas/novo"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-receita"))
                .andExpect(model().attributeExists("receita"))
                .andExpect(model().attributeExists("cafes"))
                .andExpect(model().attributeExists("tipos"));
    }

    @Test
    @DisplayName("POST /receitas deve salvar e redirecionar com dados válidos")
    void deveSalvarERedirecionar() throws Exception {
        when(receitaService.save(any())).thenReturn(Receita.builder().id(1L).proporcao("1:15").build());

        mockMvc.perform(post("/receitas")
                        .param("proporcao", "1:15")
                        .param("tempoInfusao", "180")
                        .param("notaSensorial", "4")
                        .param("ingredientes[0].nome", "açúcar")
                        .param("ingredientes[0].quantidade", "2 colheres")
                        .param("ingredientes[1].nome", "leite")
                        .param("ingredientes[1].quantidade", "100ml"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/receitas"));
    }

    @Test
    @DisplayName("POST /receitas com cafeId e tipoInfusaoId deve resolver e salvar")
    void deveSalvarComRelacionamentos() throws Exception {
        Cafe cafe = Cafe.builder().id(1L).nome("Test").build();
        TipoInfusao tipo = TipoInfusao.builder().id(1L).nome("V60").build();
        when(cafeService.findById(1L)).thenReturn(cafe);
        when(tipoInfusaoService.findById(1L)).thenReturn(tipo);
        when(receitaService.save(any())).thenReturn(Receita.builder().id(1L).build());

        mockMvc.perform(post("/receitas")
                        .param("cafeId", "1")
                        .param("tipoInfusaoId", "1")
                        .param("proporcao", "1:15")
                        .param("ingredientes[0].nome", "mel")
                        .param("ingredientes[0].quantidade", "1 colher"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/receitas"));
    }

    @Test
    @DisplayName("POST /receitas com nota inválida deve retornar formulário com erro")
    void deveRetornarFormularioComErroParaNotaInvalida() throws Exception {
        when(receitaService.save(any())).thenThrow(new IllegalArgumentException("Nota sensorial deve ser entre 1 e 5."));
        when(cafeService.findAll()).thenReturn(List.of());
        when(tipoInfusaoService.findAll()).thenReturn(List.of());

        mockMvc.perform(post("/receitas")
                        .param("notaSensorial", "6"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-receita"))
                .andExpect(model().attributeExists("erro"));
    }

    @Test
    @DisplayName("GET /receitas/{id}/editar deve retornar formulário com receita existente")
    void deveRetornarFormularioDeEdicao() throws Exception {
        when(receitaService.findById(1L)).thenReturn(Receita.builder().id(1L).proporcao("1:15").build());
        when(cafeService.findAll()).thenReturn(List.of());
        when(tipoInfusaoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/receitas/1/editar"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-receita"))
                .andExpect(model().attributeExists("receita"));
    }

    @Test
    @DisplayName("GET /receitas/{id}/editar com ingredientes existentes deve retornar formulário completo")
    void deveRetornarFormularioDeEdicaoComIngredientesExistentes() throws Exception {
        when(receitaService.findById(1L)).thenReturn(Receita.builder().id(1L).proporcao("1:15").build());
        when(cafeService.findAll()).thenReturn(List.of());
        when(tipoInfusaoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/receitas/1/editar"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-receita"))
                .andExpect(model().attributeExists("receita"));
    }

    @Test
    @DisplayName("POST /receitas/{id} deve atualizar e redirecionar com dados válidos")
    void deveAtualizarERedirecionar() throws Exception {
        when(receitaService.save(any())).thenReturn(Receita.builder().id(1L).proporcao("1:16").build());

        mockMvc.perform(post("/receitas/1")
                        .param("proporcao", "1:16")
                        .param("notaSensorial", "5")
                        .param("ingredientes[0].nome", "gengibre")
                        .param("ingredientes[0].quantidade", "1 fatia"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/receitas"));
    }

    @Test
    @DisplayName("POST /receitas/{id} com proporção inválida deve retornar formulário com erro")
    void deveRetornarFormularioComErroParaProporcaoInvalidaNaAtualizacao() throws Exception {
        when(receitaService.save(any())).thenThrow(new IllegalArgumentException("Proporção inválida. Use o formato 1:15."));
        when(cafeService.findAll()).thenReturn(List.of());
        when(tipoInfusaoService.findAll()).thenReturn(List.of());

        mockMvc.perform(post("/receitas/1")
                        .param("proporcao", "invalido"))
                .andExpect(status().isOk())
                .andExpect(view().name("form-receita"))
                .andExpect(model().attribute("erro", "Proporção inválida. Use o formato 1:15."));
    }

    @Test
    @DisplayName("GET /receitas/{id}/excluir deve excluir e redirecionar")
    void deveExcluirERedirecionar() throws Exception {
        doNothing().when(receitaService).deleteById(1L);

        mockMvc.perform(get("/receitas/1/excluir"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/receitas"));
    }
}
