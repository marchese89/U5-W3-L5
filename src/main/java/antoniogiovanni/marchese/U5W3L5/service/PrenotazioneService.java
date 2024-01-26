package antoniogiovanni.marchese.U5W3L5.service;

import antoniogiovanni.marchese.U5W3L5.exceptions.NotFoundException;
import antoniogiovanni.marchese.U5W3L5.exceptions.UnauthorizedException;
import antoniogiovanni.marchese.U5W3L5.model.Evento;
import antoniogiovanni.marchese.U5W3L5.model.Prenotazione;
import antoniogiovanni.marchese.U5W3L5.model.Ruolo;
import antoniogiovanni.marchese.U5W3L5.model.Utente;
import antoniogiovanni.marchese.U5W3L5.payloads.NewPrenotazioneDTO;
import antoniogiovanni.marchese.U5W3L5.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Prenotazione> getPrenotazioniByUser(Utente utente) {

        return prenotazioneRepository.findByUtente(utente);
    }

    public Prenotazione save(NewPrenotazioneDTO prenotazioneDTO,Utente utente) {
        Utente foundUtente = utenteService.findById(utente.getId());
        Evento evento = eventoService.findById(prenotazioneDTO.idEvento());
        //dobbiamo impedire ad un utente di prenotarsi due volte sullo stesso evento
        //vediamo se già esiste una prenotazione per lo stesso evento e lo stesso utente
        if(prenotazioneRepository.existsByUtenteAndEvento(foundUtente,evento)){
            throw new UnauthorizedException("hai già fatto una prenotazione per questo evento, non puoi farne altre");
        }
        if(prenotazioneRepository.countByEvento(evento) >= evento.getPostiDisponibili()){
            throw new UnauthorizedException("l'evento scelto non accetta più prenotazioni");
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(foundUtente);
        prenotazione.setEvento(evento);
        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione findById(UUID id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id,Utente utente) {
        Prenotazione found = this.findById(id);
        //le prenotazioni possono essere cancellate solo da organizzatore e da chi le ha fatte
        if(!utente.getId().equals(found.getUtente().getId()) || utente.getRuolo() != Ruolo.ORGANIZZATORE_EVENTI){
            throw new UnauthorizedException("non puoi cancellare prenotazioni che non hai creato se non sei un organizzatore");
        }
        prenotazioneRepository.delete(found);
    }

    public Prenotazione findByIdAndUpdate(UUID id, NewPrenotazioneDTO prenotazioneDTO,Utente utente) {

        throw new UnauthorizedException("azione non consetita");
    }
}
