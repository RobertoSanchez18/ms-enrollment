package pe.edu.vallegrande.enrollment.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.enrollment.application.service.IFamilyService;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyRequestDto;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class FamilyController {

    @Autowired
    private IFamilyService familyService;

    @GetMapping("/families")
    public Flux<FamilyResponseDto> getAll() {
        return familyService.getAll();
    }

    @GetMapping("/families/active")
    public Flux<FamilyResponseDto> getActive() {
        return familyService.getActive();
    }

    @GetMapping("/families/inactive")
    public Flux<FamilyResponseDto> getInactive() {
        return familyService.getInactive();
    }

    @GetMapping("/family/{id}")
    public Mono<FamilyResponseDto> getById(@PathVariable String id) {
        return familyService.getById(id);
    }

    @PatchMapping("/family/{id}")
    public Mono<Void> status(@PathVariable String id, @RequestBody String status) {
        return familyService.status(id, status);
    }

    @DeleteMapping("/family/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return familyService.delete(id);
    }

}
