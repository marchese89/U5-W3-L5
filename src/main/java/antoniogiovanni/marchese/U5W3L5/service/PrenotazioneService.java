package antoniogiovanni.marchese.U5W3L5.service;

import antoniogiovanni.marchese.U5W3L5.exceptions.NotFoundException;
import antoniogiovanni.marchese.U5W3L5.model.Evento;
import antoniogiovanni.marchese.U5W3L5.model.Prenotazione;
import antoniogiovanni.marchese.U5W3L5.model.Utente;
import antoniogiovanni.marchese.U5W3L5.payloads.NewPrenotazioneDTO;
import antoniogiovanni.marchese.U5W3L5.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UtenteService utenteService;

    public Page<Prenotazione> getPrenotazioni(int page, int size, String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());

        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione save(NewPrenotazioneDTO prenotazioneDTO) {
        Utente utente = utenteService.findById(prenotazioneDTO.idUtente());
        Evento evento = eventoService.findById(prenotazioneDTO.idEvento());
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);
        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione findById(UUID id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Prenotazione found = this.findById(id);
        prenotazioneRepository.delete(found);
    }

    public Prenotazione findByIdAndUpdate(UUID id, NewPrenotazioneDTO prenotazioneDTO) {
        Prenotazione found = this.findById(id);
        Utente utente = utenteService.findById(prenotazioneDTO.idUtente());
        Evento evento = eventoService.findById(prenotazioneDTO.idEvento());
        found.setUtente(utente);
        found.setEvento(evento);
        return prenotazioneRepository.save(found);
    }
}
