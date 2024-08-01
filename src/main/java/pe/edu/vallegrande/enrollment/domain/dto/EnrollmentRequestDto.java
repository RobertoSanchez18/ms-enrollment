package pe.edu.vallegrande.enrollment.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequestDto {

    private String studentId;
    private String motherId;
    private String fatherId;

}
