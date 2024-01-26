package antoniogiovanni.marchese.U5W3L5.repository;

import antoniogiovanni.marchese.U5W3L5.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
}
