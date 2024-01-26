package antoniogiovanni.marchese.U5W3L5.controller;

import antoniogiovanni.marchese.U5W3L5.model.Prenotazione;
import antoniogiovanni.marchese.U5W3L5.model.Utente;
import antoniogiovanni.marchese.U5W3L5.payloads.NewPrenotazioneDTO;
import antoniogiovanni.marchese.U5W3L5.payloads.ResponseDTO;
import antoniogiovanni.marchese.U5W3L5.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','NORMALE')")
    public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String orderBy) {
        return prenotazioneService.getPrenotazioni(page, size, orderBy);
    }
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','NORMALE')")
    public List<Prenotazione> getPrenotazioni(@AuthenticationPrincipal Utente currentUser) {
        return prenotazioneService.getPrenotazioniByUser(currentUser);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','NORMALE')")
    public ResponseDTO createPrenotazione(@RequestBody NewPrenotazioneDTO prenotazioneDTO,
                                          @AuthenticationPrincipal Utente currentUser){
        Prenotazione prenotazione = prenotazioneService.save(prenotazioneDTO,currentUser);
        return new ResponseDTO(prenotazione.getId());
    }
    @DeleteMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','NORMALE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getPrenotazioneByIdAndDelete(@PathVariable UUID prenotazioneId,@AuthenticationPrincipal Utente currentUser){
        prenotazioneService.findByIdAndDelete(prenotazioneId,currentUser);
    }

}
