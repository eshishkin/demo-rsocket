package org.eshishkin.edu.demorsocket.external.cb;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.eshishkin.edu.demorsocket.external.cb.CentralBankCurrencyResponse.CurrencyRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RussianCentralBankCurrencyService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final WebClient.Builder builder;
    private final XmlMapper mapper;

    @Value("${ext.services.cb.url}")
    private String url;

    private WebClient client;

    public RussianCentralBankCurrencyService(WebClient.Builder builder) {
        this.builder = builder;
        this.mapper = XmlMapper.xmlBuilder().addModule(new JavaTimeModule()).build();
    }

    @PostConstruct
    void init() {
        this.client = builder.baseUrl(url).build();
    }

    public Mono<List<CurrencyRecord>> getCurrencyRates(String id, LocalDate from, LocalDate to) {
        return client
                .get()
                .uri("/XML_dynamic.asp?date_req1={from}&date_req2={to}&VAL_NM_RQ={currency}",
                        from.format(FORMATTER), to.format(FORMATTER), id
                )
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::xmlToJava)
                .map(CentralBankCurrencyResponse::getRecords);
    }

    @SneakyThrows
    private CentralBankCurrencyResponse xmlToJava(String data) {
        return mapper.readValue(data, CentralBankCurrencyResponse.class);
    }
}
