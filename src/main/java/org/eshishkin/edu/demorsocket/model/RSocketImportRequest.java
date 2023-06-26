package org.eshishkin.edu.demorsocket.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RSocketImportRequest(
        @NotNull String currencyId,
        @NotNull LocalDate from, @NotNull LocalDate to) {
}
