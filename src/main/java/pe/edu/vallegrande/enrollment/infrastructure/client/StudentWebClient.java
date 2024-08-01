package pe.edu.vallegrande.enrollment.infrastructure.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.enrollment.domain.dto.StudentDto;
import pe.edu.vallegrande.enrollment.infrastructure.config.WebClientConfig;
import reactor.core.publisher.Mono;

@Service
public class StudentWebClient {

    private final WebClient.Builder webClientBuilder;
    private final String studentServiceUrl;

    @Autowired
    public StudentWebClient(WebClient.Builder webClientBuilder, @Value("${spring.vg-ms-student.url}") String studentServiceUrl) {
        this.webClientBuilder = webClientBuilder;
        this.studentServiceUrl = studentServiceUrl;
    }

    public Mono<StudentDto> getStudentById(String id) {
        return webClientBuilder.build()
                .get()
                .uri(studentServiceUrl + "/{id}", id)
                .retrieve()
                .bodyToMono(StudentDto.class);
    }

}
