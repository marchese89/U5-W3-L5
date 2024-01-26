package antoniogiovanni.marchese.U5W3L5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Evento {
    @Id
    @GeneratedValue
    private UUID id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private String luogo;
    @Column(name = "posti_disponibili")
    private int postiDisponibili;
    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    @JsonIgnore
    private Utente organizzatore;

    @OneToMany(mappedBy = "evento")
    @JsonIgnore
    private List<Prenotazione> prenotazioneList;
}
