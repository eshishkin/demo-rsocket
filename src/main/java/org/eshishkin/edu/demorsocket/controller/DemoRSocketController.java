package org.eshishkin.edu.demorsocket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eshishkin.edu.demorsocket.model.RSocketImportRequest;
import org.eshishkin.edu.demorsocket.model.RSocketImportResponse;
import org.eshishkin.edu.demorsocket.persistence.CurrencyRateRepository;
import org.eshishkin.edu.demorsocket.persistence.model.SavedCurrencyRate;
import org.eshishkin.edu.demorsocket.service.CurrencyImporter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class DemoRSocketController {

    private final CurrencyImporter importer;
    private final CurrencyRateRepository repository;

    @Validated
    @MessageMapping("import_currencies")
    public Mono<RSocketImportResponse> loadData(@Valid RSocketImportRequest request) {
        return importer.load(request);
    }

    @MessageMapping("stream_currencies")
    public Flux<SavedCurrencyRate> getCurrencies(String currencyId) {
        return repository.findAllByCbIdOrderByDateDesc(currencyId);
    }
}
