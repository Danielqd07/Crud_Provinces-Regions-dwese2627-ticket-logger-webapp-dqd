package org.iesalixar.daw2.danielquintero
        .dwese2627_ticket_logger_webapp_dqd.controllers;

import jakarta.validation.Valid;

import org.iesalixar.daw2.danielquintero
        .dwese2627_ticket_logger_webapp_dqd.daos.RegionDAO;
import org.iesalixar.daw2.danielquintero
        .dwese2627_ticket_logger_webapp_dqd.entities.Region;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Controller
public class RegionController {

    private static final Logger logger =
            LoggerFactory.getLogger(RegionController.class);

    @Autowired
    private RegionDAO regionDAO;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/regions")
    public String listRegions(Model model) {
        try {
            List<Region> listRegions = regionDAO.listAllRegions();
            model.addAttribute("listRegions", listRegions);
        } catch (SQLException e) {
            logger.error("Error al listar las regiones", e);
            model.addAttribute(
                    "errorMessage",
                    "Error al listar las regiones."
            );
        }

        return "views/region/region-list";
    }

    @GetMapping("/regions/new")
    public String showNewForm(Model model) {
        model.addAttribute("region", new Region());

        return "views/region/region-form";
    }

    @PostMapping("/regions/insert")
    public String insertRegion(
            @Valid @ModelAttribute("region") Region region,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        logger.info(
                "Insertando nueva región con código {}",
                region.getCode()
        );

        try {

            if (result.hasErrors()) {
                return "views/region/region-form";
            }

            if (regionDAO.existsRegionByCode(region.getCode())) {

                logger.warn(
                        "El código de la región {} ya existe.",
                        region.getCode()
                );

                String errorMessage =
                        messageSource.getMessage(
                                "msg.region-controller.insert.codeExist",
                                null,
                                locale
                        );

                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        errorMessage
                );

                return "redirect:/regions/new";
            }

            regionDAO.insertRegion(region);

            logger.info(
                    "Región {} insertada con éxito.",
                    region.getCode()
            );

        } catch (SQLException e) {

            logger.error(
                    "Error al insertar la región {}: {}",
                    region.getCode(),
                    e.getMessage()
            );

            String errorMessage =
                    messageSource.getMessage(
                            "msg.region-controller.insert.error",
                            null,
                            locale
                    );

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    errorMessage
            );
        }

        return "redirect:/regions";
    }

    @GetMapping("/regions/edit")
    public String showEditForm(
            @RequestParam("id") Long id,
            Model model) {

        try {

            Region region = regionDAO.getRegionById(id);

            if (region == null) {
                model.addAttribute(
                        "errorMessage",
                        "No se encontró la región."
                );

                return "views/region/region-list";
            }

            model.addAttribute("region", region);

        } catch (SQLException e) {

            logger.error(
                    "Error al obtener la región",
                    e
            );

            model.addAttribute(
                    "errorMessage",
                    "Error al obtener la región."
            );
        }

        return "views/region/region-form";
    }

    @PostMapping("/regions/update")
    public String updateRegion(
            @Valid @ModelAttribute("region") Region region,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        logger.info(
                "Actualizando región con ID {}",
                region.getId()
        );

        try {

            if (result.hasErrors()) {
                return "views/region/region-form";
            }

            if (regionDAO.existsRegionByCodeAndNotId(
                    region.getCode(),
                    region.getId())) {

                logger.warn(
                        "El código de la región {} ya existe para otra región.",
                        region.getCode()
                );

                String errorMessage =
                        messageSource.getMessage(
                                "msg.region-controller.update.codeExist",
                                null,
                                locale
                        );

                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        errorMessage
                );

                return "redirect:/regions/edit?id=" + region.getId();
            }

            regionDAO.updateRegion(region);

            logger.info(
                    "Región con ID {} actualizada con éxito.",
                    region.getId()
            );

        } catch (SQLException e) {

            logger.error(
                    "Error al actualizar la región con ID {}: {}",
                    region.getId(),
                    e.getMessage()
            );

            String errorMessage =
                    messageSource.getMessage(
                            "msg.region-controller.update.error",
                            null,
                            locale
                    );

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    errorMessage
            );
        }

        return "redirect:/regions";
    }

    @PostMapping("/regions/delete")
    public String deleteRegion(
            @RequestParam("id") Long id,
            RedirectAttributes redirectAttributes) {

        try {

            regionDAO.deleteRegion(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Región eliminada correctamente."
            );

        } catch (SQLException e) {

            logger.error(
                    "Error al eliminar la región",
                    e
            );

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error al eliminar la región."
            );
        }

        return "redirect:/regions";
    }
}