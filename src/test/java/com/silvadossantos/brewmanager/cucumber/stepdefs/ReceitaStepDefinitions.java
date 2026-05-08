package com.silvadossantos.brewmanager.cucumber.stepdefs;

import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.service.CafeService;
import com.silvadossantos.brewmanager.service.ReceitaService;
import com.silvadossantos.brewmanager.service.TipoInfusaoService;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ReceitaStepDefinitions {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private CafeService cafeService;

    @Autowired
    private TipoInfusaoService tipoInfusaoService;

    private MvcResult lastResult;
    private Long cafeId;
    private Long tipoInfusaoId;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        cafeId = null;
        tipoInfusaoId = null;
    }

    @Dado("que existe um café chamado {string} para a receita")
    public void que_existe_cafe_para_receita(String nome) {
        Cafe cafe = cafeService.save(Cafe.builder().nome(nome).build());
        cafeId = cafe.getId();
    }

    @Dado("que existe um método chamado {string} para a receita")
    public void que_existe_metodo_para_receita(String nome) {
        TipoInfusao tipo = tipoInfusaoService.save(TipoInfusao.builder().nome(nome).build());
        tipoInfusaoId = tipo.getId();
    }

    @Quando("eu cadastro uma receita com proporção {string}, tempo {int} e nota {int}")
    public void eu_cadastro_receita(String proporcao, int tempo, int nota) throws Exception {
        lastResult = mockMvc.perform(post("/receitas")
                        .param("proporcao", proporcao)
                        .param("tempoInfusao", String.valueOf(tempo))
                        .param("notaSensorial", String.valueOf(nota))
                        .param("cafeId", cafeId != null ? cafeId.toString() : "")
                        .param("tipoInfusaoId", tipoInfusaoId != null ? tipoInfusaoId.toString() : ""))
                .andReturn();
    }

    @Então("a receita deve estar salva no banco de dados")
    public void a_receita_deve_estar_salva() {
        assertThat(receitaService.findAll()).isNotEmpty();
    }

    @Quando("eu tento cadastrar uma receita com nota sensorial {int}")
    public void eu_tento_cadastrar_receita_com_nota_invalida(int nota) throws Exception {
        lastResult = mockMvc.perform(post("/receitas")
                        .param("notaSensorial", String.valueOf(nota)))
                .andReturn();
    }

    @Quando("eu tento cadastrar uma receita com proporção {string}")
    public void eu_tento_cadastrar_receita_com_proporcao_invalida(String proporcao) throws Exception {
        lastResult = mockMvc.perform(post("/receitas")
                        .param("proporcao", proporcao))
                .andReturn();
    }

    @Então("a receita não deve ser salva")
    public void a_receita_nao_deve_ser_salva() {
        assertThat(lastResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Então("o sistema deve exibir o erro {string}")
    public void o_sistema_deve_exibir_o_erro(String mensagem) throws Exception {
        String content = lastResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content).contains(mensagem);
    }
}
