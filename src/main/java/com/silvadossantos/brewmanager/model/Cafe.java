package com.silvadossantos.brewmanager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
public class Cafe {
    private String id;
    private String tipoCafe;
    private String marca;
    private List<String> notas;
    private BigDecimal preco;
}
