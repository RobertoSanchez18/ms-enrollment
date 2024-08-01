package pe.edu.vallegrande.enrollment.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttorneyDto {

    private String names;
    private String surnames;
    private String sex;
    private String birth_date;
    private String baptism;
    private String first_Communion;
    private String confirmation;
    private String marriage;
    private String relationship;
    private String email;
    private String cellphone;
    private String address;
    private String documentType;
    private String documentNumber;
}
