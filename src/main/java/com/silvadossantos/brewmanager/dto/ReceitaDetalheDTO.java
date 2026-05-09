package com.silvadossantos.brewmanager.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ReceitaDetalheDTO {
    private Long id;
    private String cafeName;
    private String cafeMarca;
    private String cafeOrigem;
    private String cafeTorra;
    private String cafeTipo;
    private String cafeMoagem;
    private String tipoInfusao;
    private String proporcao;
    private Integer tempoInfusao;
    private Integer notaSensorial;
    private String observacoes;
    private List<IngredienteDTO> ingredientes;

    @Getter
    @Builder
    public static class IngredienteDTO {
        private String nome;
        private String quantidade;
    }
}
