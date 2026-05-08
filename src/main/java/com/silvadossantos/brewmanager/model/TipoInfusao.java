package com.silvadossantos.brewmanager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tiposInfusao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoInfusao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;
}
