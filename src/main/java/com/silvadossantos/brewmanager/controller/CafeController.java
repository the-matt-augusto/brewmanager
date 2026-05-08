package com.silvadossantos.brewmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CafeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cafes")
    public String listarCafes() {
        return "lista-cafes";
    }
}
