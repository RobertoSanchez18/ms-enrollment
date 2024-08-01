package pe.edu.vallegrande.enrollment.domain.mapper;

import org.mapstruct.Mapper;
import pe.edu.vallegrande.enrollment.domain.dto.AttorneyDto;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentRequestDto;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentResponseDto;
import pe.edu.vallegrande.enrollment.domain.dto.StudentDto;
import pe.edu.vallegrande.enrollment.domain.model.Enrollment;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    EnrollmentResponseDto toEnrollmentResponseDto(Enrollment enrollment, StudentDto student, AttorneyDto mother, AttorneyDto father);
    Enrollment toEnrollment(EnrollmentRequestDto enrollmentRequestDto);

}
