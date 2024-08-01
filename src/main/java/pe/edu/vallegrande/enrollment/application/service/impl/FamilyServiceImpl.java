package pe.edu.vallegrande.enrollment.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.enrollment.application.service.IFamilyService;
import pe.edu.vallegrande.enrollment.domain.dto.*;
import pe.edu.vallegrande.enrollment.domain.mapper.FamilyMapper;
import pe.edu.vallegrande.enrollment.domain.model.Family;
import pe.edu.vallegrande.enrollment.domain.repository.FamilyRepository;
import pe.edu.vallegrande.enrollment.infrastructure.client.AttorneyWebClient;
import pe.edu.vallegrande.enrollment.infrastructure.client.StudentWebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class FamilyServiceImpl implements IFamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private StudentWebClient studentWebClient;

    @Autowired
    private AttorneyWebClient attorneyWebClient;

    @Override
    public Flux<FamilyResponseDto> getAll() {
        return familyRepository.findAllByOrderById()
                .flatMap(this::mapFamilyToDto);
    }

    @Override
    public Flux<FamilyResponseDto> getActive() {
        return familyRepository.findByStatusOrderByIdDesc(true)
                .flatMap(this::mapFamilyToDto);
    }

    @Override
    public Flux<FamilyResponseDto> getInactive() {
        return familyRepository.findByStatusOrderByIdDesc(false)
                .flatMap(this::mapFamilyToDto);
    }

    @Override
    public Mono<FamilyResponseDto> getById(String id) {
        return familyRepository.findById(id)
                .flatMap(this::mapFamilyToDto);
    }

    @Override
    public Mono<Family> getByParents(String motherId, String fatherId) {
        return familyRepository.findByMotherIdAndFatherId(motherId, fatherId);
    }

    @Override
    public Mono<FamilyResponseDto> create(FamilyRequestDto familyRequestDto) {
        Family family = familyMapper.toFamily(familyRequestDto);
        return familyRepository.save(family)
                .flatMap(this::mapFamilyToDto);
    }

    @Override
    public Mono<FamilyResponseDto> update(String id, FamilyRequestDto familyRequestDto) {
        return familyRepository.findById(id)
                .flatMap(existingFamily -> {
                    existingFamily.setStudentIds(familyRequestDto.getStudentIds());
                    existingFamily.setMotherId(familyRequestDto.getMotherId());
                    existingFamily.setFatherId(familyRequestDto.getFatherId());
                    existingFamily.setWriteDate(LocalDateTime.now());
                    return familyRepository.save(existingFamily);
                })
                .flatMap(this::mapFamilyToDto);
    }

    @Override
    public Mono<Void> status(String id, String status) {
        boolean newStatus = "A".equals(status);
        return familyRepository.findById(id)
                .flatMap(family -> {
                    family.setStatus(newStatus);
                    family.setWriteDate(LocalDateTime.now());
                    return familyRepository.save(family);
                })
                .then();
    }

    @Override
    public Mono<Void> delete(String id) {
        return familyRepository.deleteById(id);
    }

    private Mono<FamilyResponseDto> mapFamilyToDto(Family family) {
        Mono<AttorneyDto> motherMono = attorneyWebClient.getAttorneyById(family.getMotherId());
        Mono<AttorneyDto> fatherMono = attorneyWebClient.getAttorneyById(family.getFatherId());

        Flux<StudentDto> studentsFlux = Flux.fromIterable(
                family.getStudentIds() != null ? family.getStudentIds() : Collections.emptySet()
        ).flatMap(studentWebClient::getStudentById);

        return Mono.zip(motherMono, fatherMono, studentsFlux.collectList())
                .map(tuple -> {
                    AttorneyDto mother = tuple.getT1();
                    AttorneyDto father = tuple.getT2();
                    Set<StudentDto> students = new HashSet<>(tuple.getT3());
                    return familyMapper.toFamilyResponseDto(family, students, mother, father);
                });
    }

}