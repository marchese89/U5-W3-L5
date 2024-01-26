package antoniogiovanni.marchese.U5W3L5.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Prenotazione {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}
