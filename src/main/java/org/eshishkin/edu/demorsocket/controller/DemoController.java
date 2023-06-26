package org.eshishkin.edu.demorsocket.controller;

import lombok.RequiredArgsConstructor;
import org.eshishkin.edu.demorsocket.model.RSocketImportRequest;
import org.eshishkin.edu.demorsocket.model.RSocketImportResponse;
import org.eshishkin.edu.demorsocket.persistence.model.SavedCurrencyRate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class DemoController {

    private final RSocketRequester requester;

    @GetMapping("/import")
    public Mono<RSocketImportResponse> loadData(@RequestParam String currency,
                                                @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate from,
                                                @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate to) {
        return requester
                .route("import_currencies")
                .data(new RSocketImportRequest(currency, from, to))
                .retrieveMono(RSocketImportResponse.class);
    }

    @GetMapping("/currencies")
    public Flux<SavedCurrencyRate> getCurrencies(@RequestParam String currency) {
        return requester
                .route("stream_currencies")
                .data(currency)
                .retrieveFlux(SavedCurrencyRate.class);
    }
}
