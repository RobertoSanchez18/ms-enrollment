package pe.edu.vallegrande.enrollment.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "enrollment")
public class Enrollment {

    @MongoId
    @Field("_id")
    private String id;

    @Field("student_id")
    private String studentId;

    @Field("mother_id")
    private String motherId;

    @Field("father_id")
    private String fatherId;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field("creation_date")
    private LocalDateTime creationDate=LocalDateTime.now();

    @LastModifiedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field("write_date")
    private LocalDateTime writeDate=LocalDateTime.now();

    @Field("status")
    private String status="P";

}
