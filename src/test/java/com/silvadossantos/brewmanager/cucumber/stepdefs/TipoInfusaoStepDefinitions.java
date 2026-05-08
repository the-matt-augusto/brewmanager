package com.silvadossantos.brewmanager.cucumber.stepdefs;

import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.service.TipoInfusaoService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TipoInfusaoStepDefinitions {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private TipoInfusaoService tipoInfusaoService;

    private MvcResult lastResult;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Dado("que existem os seguintes métodos de infusão cadastrados:")
    public void que_existem_metodos_cadastrados(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            tipoInfusaoService.save(TipoInfusao.builder()
                    .nome(row.get("nome"))
                    .descricao(row.get("descricao"))
                    .build());
        }
    }

    @Quando("eu acesso a lista de métodos de infusão")
    public void eu_acesso_lista_metodos() throws Exception {
        lastResult = mockMvc.perform(get("/tipos-infusao"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Então("eu devo ver o método {string} na lista")
    public void eu_devo_ver_o_metodo_na_lista(String nome) throws Exception {
        String content = lastResult.getResponse().getContentAsString();
        assertThat(content).contains(nome);
    }

    @Dado("que eu estou na página de cadastro de método")
    public void que_eu_estou_na_pagina_de_cadastro_de_metodo() {
        // estado implícito, sem ação necessária
    }

    @Quando("eu cadastro o método com nome {string} e descrição {string}")
    public void eu_cadastro_metodo(String nome, String descricao) throws Exception {
        mockMvc.perform(post("/tipos-infusao")
                        .param("nome", nome)
                        .param("descricao", descricao))
                .andExpect(status().is3xxRedirection());
    }

    @Então("o método {string} deve estar salvo")
    public void o_metodo_deve_estar_salvo(String nome) {
        List<TipoInfusao> tipos = tipoInfusaoService.findAll();
        assertThat(tipos).anyMatch(t -> t.getNome().equals(nome));
    }
}
