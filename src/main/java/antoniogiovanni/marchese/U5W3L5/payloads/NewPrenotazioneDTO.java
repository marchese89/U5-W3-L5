package antoniogiovanni.marchese.U5W3L5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewPrenotazioneDTO(
        @NotNull(message = "l'id utente non può essere null")
        @NotEmpty(message = "l'id utente non può essere vuoto")
        UUID idUtente,
        @NotNull(message = "l'id evento non può essere null")
        @NotEmpty(message = "l'id evento non può essere vuoto")
        UUID idEvento) {
}
