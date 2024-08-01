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
public class FamilyRequestDto {

    private Set<String> studentIds;
    private String motherId;
    private String fatherId;

}
