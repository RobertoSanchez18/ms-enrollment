package pe.edu.vallegrande.enrollment.infrastructure.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.enrollment.domain.dto.AttorneyDto;
import reactor.core.publisher.Mono;

@Service
public class AttorneyWebClient {

    private final WebClient.Builder webClientBuilder;
    private final String attorneyServiceUrl;

    @Autowired
    public AttorneyWebClient(WebClient.Builder webClientBuilder, @Value("${spring.vg-ms-attorney.url}") String attorneyServiceUrl) {
        this.webClientBuilder = webClientBuilder;
        this.attorneyServiceUrl = attorneyServiceUrl;
    }

    public Mono<AttorneyDto> getAttorneyById(String id) {
        return webClientBuilder.build()
                .get()
                .uri(attorneyServiceUrl + "/{id}", id)
                .retrieve()
                .bodyToMono(AttorneyDto.class);
    }

}
