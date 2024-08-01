package pe.edu.vallegrande.enrollment.application.service;

import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentRequestDto;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEnrollmentService {

    Flux<EnrollmentResponseDto> getAll();
    Flux<EnrollmentResponseDto> getActive();
    Flux<EnrollmentResponseDto> getInactive();
    Flux<EnrollmentResponseDto> getPending();
    Mono<EnrollmentResponseDto> getById(String id);
    Mono<EnrollmentResponseDto> create(EnrollmentRequestDto enrollmentRequestDto);
    Mono<EnrollmentResponseDto> update(String id, EnrollmentRequestDto enrollmentRequestDto);
    Mono<Void> status(String id, String status);
    Mono<Void> delete(String id);

}
