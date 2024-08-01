package pe.edu.vallegrande.enrollment.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDto {

    private String id;
    private StudentDto student;
    private AttorneyDto mother;
    private AttorneyDto father;
    private String creationDate;
    private String writeDate;
    private String status;

}
