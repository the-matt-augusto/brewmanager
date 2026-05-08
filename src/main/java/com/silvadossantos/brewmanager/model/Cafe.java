package com.silvadossantos.brewmanager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cafes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "marca_torrefacao")
    private String marcaTorrefacao;

    private String origem;

    @Column(name = "nivel_torra")
    private String nivelTorra;

    @Column(name = "tipo_cafe")
    private String tipoCafe;

    @Column(name = "nivel_moagem")
    private String nivelMoagem;
}
