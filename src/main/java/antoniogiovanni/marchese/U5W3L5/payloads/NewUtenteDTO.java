package antoniogiovanni.marchese.U5W3L5.payloads;

import antoniogiovanni.marchese.U5W3L5.model.Ruolo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
public record NewUtenteDTO(
        @NotEmpty(message = "il nome non può essere vuoto")
        @NotNull(message = "il nome non può essere null")
        String nome,
        @NotEmpty(message = "il cognome non può essere vuoto")
        @NotNull(message = "il cognome non può essere null")
        String cognome,
        @NotEmpty(message = "l'email non può essere vuota")
        @NotNull(message = "l'email non può essere null")
        @Email(message = "email non formattata correttamente")
        String email,
        @NotEmpty(message = "la password non può essere vuota")
        @NotNull(message = "la password non può essere null")
        String password,
        Ruolo ruolo
) {
}
