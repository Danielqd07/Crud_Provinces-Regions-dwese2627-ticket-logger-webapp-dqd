package org.iesalixar.daw2.danielquintero.dwese2627_ticket_logger_webapp_dqd.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}