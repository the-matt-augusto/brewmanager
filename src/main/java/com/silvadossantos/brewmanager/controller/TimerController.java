package com.silvadossantos.brewmanager.controller;

import com.silvadossantos.brewmanager.service.ReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/timer")
@RequiredArgsConstructor
public class TimerController {

    private final ReceitaService receitaService;

    @GetMapping
    public String timer(Model model) {
        model.addAttribute("receitas", receitaService.findAll());
        return "timer";
    }
}
