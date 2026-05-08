package com.silvadossantos.brewmanager.service;

import com.silvadossantos.brewmanager.model.Receita;
import com.silvadossantos.brewmanager.repository.ReceitaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceitaServiceTest {

    @Mock
    private ReceitaRepository receitaRepository;

    @InjectMocks
    private ReceitaService receitaService;

    @Test
    @DisplayName("Deve retornar uma lista de todas as receitas")
    void deveRetornarListaDeTodasAsReceitas() {
        Receita receita = Receita.builder().id(1L).proporcao("1:15").build();
        when(receitaRepository.findAll()).thenReturn(List.of(receita));

        List<Receita> result = receitaService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProporcao()).isEqualTo("1:15");
        verify(receitaRepository).findAll();
    }

    @Test
    @DisplayName("Deve salvar e retornar uma receita com dados válidos")
    void deveSalvarERetornarReceita() {
        Receita receita = Receita.builder().proporcao("1:16").notaSensorial(4).build();
        Receita savedReceita = Receita.builder().id(1L).proporcao("1:16").notaSensorial(4).build();
        when(receitaRepository.save(any(Receita.class))).thenReturn(savedReceita);

        Receita result = receitaService.save(receita);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getProporcao()).isEqualTo("1:16");
        verify(receitaRepository).save(receita);
    }

    @Test
    @DisplayName("Deve lançar exceção para nota sensorial inválida (muito baixa)")
    void deveLancarExcecaoParaNotaBaixaInvalida() {
        Receita receita = Receita.builder().notaSensorial(0).build();
        assertThatThrownBy(() -> receitaService.save(receita))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nota sensorial deve ser entre 1 e 5.");
    }

    @Test
    @DisplayName("Deve lançar exceção para nota sensorial inválida (muito alta)")
    void deveLancarExcecaoParaNotaAltaInvalida() {
        Receita receita = Receita.builder().notaSensorial(6).build();
        assertThatThrownBy(() -> receitaService.save(receita))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nota sensorial deve ser entre 1 e 5.");
    }

    @Test
    @DisplayName("Deve lançar exceção para formato de proporção inválido")
    void deveLancarExcecaoParaProporcaoInvalida() {
        Receita receita = Receita.builder().proporcao("invalid").build();
        assertThatThrownBy(() -> receitaService.save(receita))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Proporção inválida. Use o formato 1:15.");
    }

    @Test
    @DisplayName("Deve encontrar receita por id")
    void deveEncontrarReceitaPorId() {
        Receita receita = Receita.builder().id(1L).proporcao("1:15").build();
        when(receitaRepository.findById(1L)).thenReturn(Optional.of(receita));

        Receita result = receitaService.findById(1L);

        assertThat(result.getProporcao()).isEqualTo("1:15");
    }

    @Test
    @DisplayName("Deve lançar exceção quando a receita não for encontrada")
    void deveLancarExcecaoQuandoReceitaNaoEncontrada() {
        when(receitaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> receitaService.findById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Receita não encontrada: 1");
    }

    @Test
    @DisplayName("Deve excluir receita por id")
    void deveExcluirReceitaPorId() {
        doNothing().when(receitaRepository).deleteById(1L);

        receitaService.deleteById(1L);

        verify(receitaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve aceitar proporção nula e nota nula")
    void deveAceitarValoresNulos() {
        Receita receita = Receita.builder().build();
        when(receitaRepository.save(any(Receita.class))).thenReturn(receita);

        receitaService.save(receita);

        verify(receitaRepository).save(receita);
    }

    @Test
    @DisplayName("Deve aceitar proporção em branco sem lançar exceção")
    void deveAceitarProporcaoEmBranco() {
        Receita receita = Receita.builder().proporcao("").build();
        when(receitaRepository.save(any(Receita.class))).thenReturn(receita);

        receitaService.save(receita);

        verify(receitaRepository).save(receita);
    }
}
