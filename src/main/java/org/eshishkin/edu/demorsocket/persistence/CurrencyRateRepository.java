package org.eshishkin.edu.demorsocket.persistence;

import org.eshishkin.edu.demorsocket.persistence.model.SavedCurrencyRate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CurrencyRateRepository extends ReactiveMongoRepository<SavedCurrencyRate, String> {

    Flux<SavedCurrencyRate> findAllByCbIdOrderByDateDesc(String id);
}
