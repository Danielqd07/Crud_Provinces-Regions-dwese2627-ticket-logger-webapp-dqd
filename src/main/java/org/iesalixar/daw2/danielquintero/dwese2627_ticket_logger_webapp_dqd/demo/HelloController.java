package org.iesalixar.daw2.danielquintero.dwese2627_ticket_logger_webapp_dqd.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    private static final Logger logger =
            LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    @ResponseBody
    public String hello(
            @RequestParam(value = "name", defaultValue = "World") String name) {

        logger.info("Entrando en hello con name={}", name);

        String message = "Hello " + name + "!";

        logger.info("Saliendo de hello");

        return message;
    }
}