package com.silvadossantos.brewmanager.cucumber.stepdefs;

import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.service.CafeService;
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

public class CafeStepDefinitions {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private CafeService cafeService;

    private MvcResult lastResult;
    private Cafe cafeToSave;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Dado("que existem os seguintes cafés cadastrados:")
    public void que_existem_os_seguintes_cafés_cadastrados(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            Cafe cafe = Cafe.builder()
                    .nome(row.get("nome"))
                    .marcaTorrefacao(row.get("marca_torrefacao"))
                    .origem(row.get("origem"))
                    .nivelTorra(row.get("nivel_torra"))
                    .build();
            cafeService.save(cafe);
        }
    }

    @Quando("eu acesso a lista de cafés")
    public void eu_acesso_a_lista_de_cafés() throws Exception {
        lastResult = mockMvc.perform(get("/cafes"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Então("eu devo ver {string} na lista")
    public void eu_devo_ver_na_lista(String nome) throws Exception {
        String content = lastResult.getResponse().getContentAsString();
        assertThat(content).contains(nome);
    }

    @Dado("que eu estou na página de cadastro de café")
    public void que_eu_estou_na_página_de_cadastro_de_café() {
        cafeToSave = new Cafe();
    }

    @Quando("eu preencho o nome com {string}")
    public void eu_preencho_o_nome_com(String nome) {
        cafeToSave.setNome(nome);
    }

    @Quando("a marca com {string}")
    public void a_marca_com(String marca) {
        cafeToSave.setMarcaTorrefacao(marca);
    }

    @Quando("a origem com {string}")
    public void a_origem_com(String origem) {
        cafeToSave.setOrigem(origem);
    }

    @Quando("o nível de torra com {string}")
    public void o_nível_de_torra_com(String torra) {
        cafeToSave.setNivelTorra(torra);
    }

    @Quando("eu clico em salvar")
    public void eu_clico_em_salvar() throws Exception {
        // Simula o envio do formulário via POST
        mockMvc.perform(post("/cafes")
                .param("nome", cafeToSave.getNome())
                .param("marcaTorrefacao", cafeToSave.getMarcaTorrefacao())
                .param("origem", cafeToSave.getOrigem())
                .param("nivelTorra", cafeToSave.getNivelTorra()))
                .andExpect(status().is3xxRedirection());
    }

    @Então("o café {string} deve estar salvo no banco de dados")
    public void o_café_deve_estar_salvo_no_banco_de_dados(String nome) {
        List<Cafe> cafes = cafeService.findAll();
        boolean found = cafes.stream().anyMatch(c -> c.getNome().equals(nome));
        assertThat(found).isTrue();
    }
}
