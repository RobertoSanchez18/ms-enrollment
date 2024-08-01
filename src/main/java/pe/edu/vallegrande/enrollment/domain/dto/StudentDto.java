package pe.edu.vallegrande.enrollment.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String name;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private String gender;
    private LocalDate birthDate;
    private String baptism;
    private String communion;
    private String email;
    private String birthPlace;
    private String level;
    private String grade;
    private String section;

}
