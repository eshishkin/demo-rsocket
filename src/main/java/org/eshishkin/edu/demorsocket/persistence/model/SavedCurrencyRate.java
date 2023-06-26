package org.eshishkin.edu.demorsocket.persistence.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Document(collection = "quotes")
public class SavedCurrencyRate {

    @Id
    private String id;

    private String cbId;

    private LocalDate date;

    private BigDecimal value;
}
