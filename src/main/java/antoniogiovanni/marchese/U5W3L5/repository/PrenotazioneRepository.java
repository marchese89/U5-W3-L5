package antoniogiovanni.marchese.U5W3L5.repository;

import antoniogiovanni.marchese.U5W3L5.model.Evento;
import antoniogiovanni.marchese.U5W3L5.model.Prenotazione;
import antoniogiovanni.marchese.U5W3L5.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findByUtente(Utente utente);
    boolean existsByUtenteAndEvento(Utente utente, Evento evento);

    long countByEvento(Evento evento);
}
