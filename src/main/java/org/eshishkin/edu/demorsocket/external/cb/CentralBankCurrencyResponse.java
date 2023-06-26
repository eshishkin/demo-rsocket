package org.eshishkin.edu.demorsocket.external.cb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "ValCurs")
public class CentralBankCurrencyResponse {

    @JsonProperty("Record")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CurrencyRecord> records;//;; = new ArrayList<>();

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyRecord {
        @JacksonXmlProperty(localName = "Id", isAttribute = true)
        private String id;

        @JsonFormat(pattern = "dd.MM.yyyy")
        @JacksonXmlProperty(localName = "Date", isAttribute = true)
        private LocalDate date;

        @JacksonXmlProperty(localName = "Value")
        private String value;
    }
}
