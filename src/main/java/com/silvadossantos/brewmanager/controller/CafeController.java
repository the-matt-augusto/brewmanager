package com.silvadossantos.brewmanager.controller;

import com.silvadossantos.brewmanager.exception.EntityInUseException;
import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cafes")
    public String listar(Model model) {
        model.addAttribute("cafes", cafeService.findAll());
        return "lista-cafes";
    }

    @GetMapping("/cafes/novo")
    public String novoForm(Model model) {
        model.addAttribute("cafe", new Cafe());
        return "form-cafe";
    }

    @PostMapping("/cafes")
    public String salvar(@ModelAttribute Cafe cafe) {
        cafeService.save(cafe);
        return "redirect:/cafes";
    }

    @GetMapping("/cafes/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("cafe", cafeService.findById(id));
        return "form-cafe";
    }

    @PostMapping("/cafes/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Cafe cafe) {
        cafe.setId(id);
        cafeService.save(cafe);
        return "redirect:/cafes";
    }

    @GetMapping("/cafes/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cafeService.deleteById(id);
        } catch (EntityInUseException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/cafes";
    }
}
