package pe.edu.vallegrande.enrollment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyResponseDto {

    private String id;
    private Set<StudentDto> students;
    private AttorneyDto mother;
    private AttorneyDto father;
    private String creationDate;
    private String writeDate;

}
