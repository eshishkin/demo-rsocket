package org.eshishkin.edu.demorsocket.service;

import lombok.RequiredArgsConstructor;
import org.eshishkin.edu.demorsocket.external.cb.RussianCentralBankCurrencyService;
import org.eshishkin.edu.demorsocket.model.RSocketImportRequest;
import org.eshishkin.edu.demorsocket.model.RSocketImportResponse;
import org.eshishkin.edu.demorsocket.persistence.CurrencyRateRepository;
import org.eshishkin.edu.demorsocket.persistence.model.SavedCurrencyRate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.replace;

@Service
@RequiredArgsConstructor
public class CurrencyImporter {

    private final CurrencyRateRepository repository;
    private final RussianCentralBankCurrencyService external;

    public Mono<RSocketImportResponse> load(RSocketImportRequest request) {
        return external.getCurrencyRates(request.currencyId(), request.from(), request.to())
                .flatMapIterable(Function.identity())
                .flatMap(currency -> {
                    var entity = new SavedCurrencyRate()
                            .setId(currency.getId() + "_" + currency.getDate())
                            .setCbId(currency.getId())
                            .setDate(currency.getDate())
                            .setValue(new BigDecimal(
                                    replace(currency.getValue(), ",", ".")
                            ));
                    return repository.save(entity);
                })
                .collectList()
                .map(List::size)
                .map(RSocketImportResponse::new);
    }
}
