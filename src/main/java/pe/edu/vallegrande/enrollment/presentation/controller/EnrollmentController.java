package pe.edu.vallegrande.enrollment.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.enrollment.application.service.IEnrollmentService;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentRequestDto;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

    @Autowired
    private IEnrollmentService enrollmentService;

    @GetMapping("/enrollments")
    public Flux<EnrollmentResponseDto> getAll() {
        return enrollmentService.getAll();
    }

    @GetMapping("/enrollments/active")
    public Flux<EnrollmentResponseDto> getActive() {
        return enrollmentService.getActive();
    }

    @GetMapping("https://8400-personal290-msenrollmen-ywt7znxpqq0.ws-us115.gitpod.io")
    public Flux<EnrollmentResponseDto> getInactive() {
        return enrollmentService.getInactive();
    }

    @GetMapping("/enrollments/pendings")
    public Flux<EnrollmentResponseDto> getPendings() {
        return enrollmentService.getPending();
    }

    @GetMapping("/enrollment/{id}")
    public Mono<EnrollmentResponseDto> getById(@PathVariable String id) {
        return enrollmentService.getById(id);
    }

    @PostMapping("/enrollment/")
    public Mono<EnrollmentResponseDto> create(@RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        return enrollmentService.create(enrollmentRequestDto);
    }

    @PutMapping("/enrollment/{id}")
    public Mono<EnrollmentResponseDto> update(@PathVariable String id, @RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        return enrollmentService.update(id, enrollmentRequestDto);
    }

    @PatchMapping("/enrollment/{id}")
    public Mono<Void> status(@PathVariable String id, @RequestBody String status) {
        return enrollmentService.status(id, status);
    }

    @DeleteMapping("/enrollment/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return enrollmentService.delete(id);
    }

}
