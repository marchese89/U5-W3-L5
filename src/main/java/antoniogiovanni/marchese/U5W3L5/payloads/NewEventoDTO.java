package antoniogiovanni.marchese.U5W3L5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record NewEventoDTO(
        @NotNull(message = "il titolo dell'evento non può essere null")
        @NotEmpty(message = "il titolo dell'evento non può essere vuoto")
        String titolo,
        @NotNull(message = "la descrizione dell'evento non può essere null")
        @NotEmpty(message = "la descrizione dell'evento non può essere vuota")
         String descrizione,
        @NotNull(message = "la data dell'evento non può essere null")
         LocalDate data,
        @NotNull(message = "il luogo dell'evento non può essere null")
        @NotEmpty(message = "il luogo dell'evento non può essere vuoto")
        String luogo,
        int postiDisponibili
) {
}
