package antoniogiovanni.marchese.U5W3L5.service;

import antoniogiovanni.marchese.U5W3L5.exceptions.NotFoundException;
import antoniogiovanni.marchese.U5W3L5.model.Utente;
import antoniogiovanni.marchese.U5W3L5.payloads.NewUtenteDTO;
import antoniogiovanni.marchese.U5W3L5.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    private PasswordEncoder bcrypt =  new BCryptPasswordEncoder(11);

    public Page<Utente> getUsers(int page, int size, String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());

        return utenteRepository.findAll(pageable);
    }

    public Utente save(NewUtenteDTO utenteDTO) {
        Utente newUtente = new Utente();
        newUtente.setNome(utenteDTO.nome());
        newUtente.setCognome(utenteDTO.cognome());
        newUtente.setEmail(utenteDTO.email());
        newUtente.setPassword(bcrypt.encode(utenteDTO.password()));
        newUtente.setRuolo(utenteDTO.ruolo());
        return utenteRepository.save(newUtente);
    }

    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Utente found = this.findById(id);
        utenteRepository.delete(found);
    }

    public Utente findByIdAndUpdate(UUID id, NewUtenteDTO utente) {
        Utente found = this.findById(id);
        found.setEmail(utente.email());
        found.setNome(utente.nome());
        found.setCognome(utente.cognome());
        found.setRuolo(utente.ruolo());
        return utenteRepository.save(found);
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
}
