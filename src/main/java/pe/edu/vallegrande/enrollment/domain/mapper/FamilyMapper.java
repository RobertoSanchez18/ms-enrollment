package pe.edu.vallegrande.enrollment.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pe.edu.vallegrande.enrollment.domain.dto.*;
import pe.edu.vallegrande.enrollment.domain.model.Family;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FamilyMapper {

    @Mappings({
            @Mapping(target = "id", source = "family.id"),
            @Mapping(target = "students", source = "studentsDto"),
            @Mapping(target = "mother", source = "mother"),
            @Mapping(target = "father", source = "father"),
            @Mapping(target = "creationDate", source = "family.creationDate", dateFormat = "dd-MM-yyyy HH:mm:ss"),
            @Mapping(target = "writeDate", source = "family.writeDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    FamilyResponseDto toFamilyResponseDto(Family family, Set<StudentDto> studentsDto, AttorneyDto mother, AttorneyDto father);
    Family toFamily(FamilyRequestDto familyRequestDto);
    //FamilyRequestDto toFamilyRequestDto(Family family);

    default FamilyRequestDto toFamilyRequestDto(Family family) {
        FamilyRequestDto dto = new FamilyRequestDto();
        dto.setStudentIds(family.getStudentIds() != null ? family.getStudentIds() : new HashSet<>());
        dto.setMotherId(family.getMotherId());
        dto.setFatherId(family.getFatherId());
        return dto;
    }

}
