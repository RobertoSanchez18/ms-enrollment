package pe.edu.vallegrande.enrollment.application.service;

import pe.edu.vallegrande.enrollment.domain.dto.FamilyRequestDto;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyResponseDto;
import pe.edu.vallegrande.enrollment.domain.model.Family;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFamilyService {

    Flux<FamilyResponseDto> getAll();
    Flux<FamilyResponseDto> getActive();
    Flux<FamilyResponseDto> getInactive();
    Mono<FamilyResponseDto> getById(String id);
    Mono<Family> getByParents(String motherId, String fatherId);
    Mono<FamilyResponseDto> create(FamilyRequestDto familyRequestDto);
    Mono<FamilyResponseDto> update(String id, FamilyRequestDto familyRequestDto);
    Mono<Void> status(String id, String status);
    Mono<Void> delete(String id);

}
