package pe.edu.vallegrande.enrollment.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.enrollment.domain.model.Family;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FamilyRepository extends ReactiveMongoRepository<Family, String> {

    Flux<Family> findAllByOrderById();
    Flux<Family> findByStatusOrderByIdDesc(Boolean status);
    Mono<Family> findByMotherIdAndFatherId(String motherId, String fatherId);

}
