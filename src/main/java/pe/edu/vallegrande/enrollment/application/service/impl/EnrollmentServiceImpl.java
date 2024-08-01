package pe.edu.vallegrande.enrollment.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.enrollment.application.service.IEnrollmentService;
import pe.edu.vallegrande.enrollment.application.service.IFamilyService;
import pe.edu.vallegrande.enrollment.domain.dto.*;
import pe.edu.vallegrande.enrollment.domain.mapper.EnrollmentMapper;
import pe.edu.vallegrande.enrollment.domain.mapper.FamilyMapper;
import pe.edu.vallegrande.enrollment.domain.model.Enrollment;
import pe.edu.vallegrande.enrollment.domain.repository.EnrollmentRepository;
import pe.edu.vallegrande.enrollment.infrastructure.client.AttorneyWebClient;
import pe.edu.vallegrande.enrollment.infrastructure.client.StudentWebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private IFamilyService familyService;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private StudentWebClient studentWebClient;

    @Autowired
    private AttorneyWebClient attorneyWebClient;

    @Override
    public Flux<EnrollmentResponseDto> getAll() {
        return enrollmentRepository.findAllByOrderById()
                .flatMap(this::mapEnrollmentToDto);
    }

    @Override
    public Flux<EnrollmentResponseDto> getActive() {
        return enrollmentRepository.findByStatusOrderByIdDesc("A")
                .flatMap(this::mapEnrollmentToDto);
    }

    @Override
    public Flux<EnrollmentResponseDto> getInactive() {
        return enrollmentRepository.findByStatusOrderByIdDesc("I")
                .flatMap(this::mapEnrollmentToDto);
    }

    @Override
    public Flux<EnrollmentResponseDto> getPending() {
        return enrollmentRepository.findByStatusOrderByIdDesc("P")
                .flatMap(this::mapEnrollmentToDto);
    }

    @Override
    public Mono<EnrollmentResponseDto> getById(String id) {
        return enrollmentRepository.findById(id)
                .flatMap(this::mapEnrollmentToDto);
    }

    @Override
    public Mono<EnrollmentResponseDto> create(EnrollmentRequestDto enrollmentRequestDto) {
        Enrollment enrollment = enrollmentMapper.toEnrollment(enrollmentRequestDto);
        return enrollmentRepository.save(enrollment)
                .flatMap(this::mapEnrollmentToDto);
    }

    @Override
    public Mono<EnrollmentResponseDto> update(String id, EnrollmentRequestDto enrollmentRequestDto) {
        return enrollmentRepository.findById(id)
                .flatMap(enrollmentId -> {
                    enrollmentId.setStudentId(enrollmentRequestDto.getStudentId());
                    enrollmentId.setMotherId(enrollmentRequestDto.getMotherId());
                    enrollmentId.setFatherId(enrollmentRequestDto.getFatherId());
                    enrollmentId.setWriteDate(LocalDateTime.now());
                    return enrollmentRepository.save(enrollmentId);
                })
                .flatMap(this::mapEnrollmentToDto);
    }

    @Override
    public Mono<Void> status(String id, String status) {
        return enrollmentRepository.findById(id)
                .flatMap(enrollmentId -> {
                    enrollmentId.setStatus(status);
                    return enrollmentRepository.save(enrollmentId)
                            .flatMap(savedEnrollment -> {
                                if ("A".equals(status)) {
                                    return manageFamilyRecord(savedEnrollment);
                                }
                                return Mono.empty();
                            });
                })
                .then();
    }

    @Override
    public Mono<Void> delete(String id) {
        return enrollmentRepository.deleteById(id);
    }

    private Mono<EnrollmentResponseDto> mapEnrollmentToDto(Enrollment enrollment) {
        Mono<StudentDto> studentMono = studentWebClient.getStudentById(enrollment.getStudentId());
        Mono<AttorneyDto> motherMono = attorneyWebClient.getAttorneyById(enrollment.getMotherId());
        Mono<AttorneyDto> fatherMono = attorneyWebClient.getAttorneyById(enrollment.getFatherId());

        return Mono.zip(studentMono, motherMono, fatherMono)
                .map(tuple -> {
                    StudentDto student = tuple.getT1();
                    AttorneyDto mother = tuple.getT2();
                    AttorneyDto father = tuple.getT3();
                    return enrollmentMapper.toEnrollmentResponseDto(enrollment, student, mother, father);
                });
    }

    private Mono<Void> manageFamilyRecord(Enrollment enrollment) {
        String studentId = enrollment.getStudentId();
        String motherId = enrollment.getMotherId();
        String fatherId = enrollment.getFatherId();

        return familyService.getByParents(motherId, fatherId)
                .flatMap(family -> {
                    if (family.getStudentIds() == null) {
                        family.setStudentIds(new HashSet<>());
                    }
                    family.getStudentIds().add(studentId);

                    FamilyRequestDto familyRequestDto = familyMapper.toFamilyRequestDto(family);
                    return familyService.update(family.getId(), familyRequestDto);
                })
                .switchIfEmpty(
                        familyService.create(new FamilyRequestDto(new HashSet<>(Set.of(studentId)), motherId, fatherId))
                )
                .then();
    }

}
