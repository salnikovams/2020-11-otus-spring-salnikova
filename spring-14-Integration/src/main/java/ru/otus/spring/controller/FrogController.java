package ru.otus.spring.controller;

import org.hibernate.JDBCException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Frog;
import ru.otus.spring.dto.FrogDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.integration.FrogGateway;
import ru.otus.spring.service.FrogService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FrogController {

    private final FrogService frogService;


    private FrogGateway frogGateway;


    public FrogController(FrogService frogService, FrogGateway frogGateway) {
        this.frogService = frogService;
        this.frogGateway = frogGateway;
    }

    @GetMapping("/frogs")
    public String getAllfrogs(Model model) {
        List<Frog> frogs = frogService.getAllFrogs();
        List<FrogDto> frogDtos = frogs.stream()
                .map(FrogDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("frogs", frogDtos);
        return "list";
    }

    @PostMapping("/frogs/add")
    public String addNewFrog(@RequestParam(name = "name") String name,
                         Model model) {

        Frog frog = frogGateway.processNewFrog( new Frog (name));
        model.addAttribute("addResult", frog.getId());
        return "redirect:/frogs";
    }

    @GetMapping("/frogs/delete")
    public String delete(@RequestParam("id") long id) {
        frogService.deleteFrog(id);
        return "redirect:/frogs";
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Not found");
    }

    @ExceptionHandler(JDBCException.class)
    public ResponseEntity<String> handleException(JDBCException ex) {
        return ResponseEntity.badRequest().body(ex.getSQLException().getMessage());
    }

}
