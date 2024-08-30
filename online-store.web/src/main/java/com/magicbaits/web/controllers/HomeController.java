package com.magicbaits.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
    public String index() {
        return "redirect:/index.html"; // Carga src/main/resources/static/index.html
    }
}
