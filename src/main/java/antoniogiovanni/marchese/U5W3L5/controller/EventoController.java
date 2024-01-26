package antoniogiovanni.marchese.U5W3L5.controller;

import antoniogiovanni.marchese.U5W3L5.exceptions.BadRequestException;
import antoniogiovanni.marchese.U5W3L5.exceptions.UnauthorizedException;
import antoniogiovanni.marchese.U5W3L5.model.Evento;
import antoniogiovanni.marchese.U5W3L5.model.Utente;
import antoniogiovanni.marchese.U5W3L5.payloads.NewEventoDTO;
import antoniogiovanni.marchese.U5W3L5.payloads.ResponseDTO;
import antoniogiovanni.marchese.U5W3L5.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventoController {

    @Autowired
    private EventoService eventoService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','NORMALE')")
    public Page<Evento> getUsers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return eventoService.getEventi(page, size, orderBy);
    }
    @GetMapping("/{idEvento}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','NORMALE')")
    public Evento getEventoById(@PathVariable UUID idEvento){
        return eventoService.findById(idEvento);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public ResponseDTO createEvent( @RequestBody @Validated NewEventoDTO eventoDTO, BindingResult validation,@AuthenticationPrincipal Utente currentUser){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }
        Evento evento = eventoService.save(eventoDTO,currentUser);
        return new ResponseDTO(evento.getId());
    }
    @PutMapping("/{idEvento}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public ResponseDTO modifyEvent( @RequestBody @Validated NewEventoDTO eventoDTO,@PathVariable UUID idEvento ,BindingResult validation,@AuthenticationPrincipal Utente currentUser){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }
        Evento found = eventoService.findById(idEvento);
        //solo chi ha creato un evento lo può modificare
        if (!currentUser.getId().equals(found.getOrganizzatore().getId())){
            throw new UnauthorizedException("l'utente corrente non è il creatore dell'evento e non lo può modificare");
        }
        Evento evento = eventoService.findByIdAndUpdate(idEvento,eventoDTO);
        return new ResponseDTO(evento.getId());
    }
    @DeleteMapping("/{idEvento}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public void deleteEvent(@PathVariable UUID idEvento ,@AuthenticationPrincipal Utente currentUser){

        Evento found = eventoService.findById(idEvento);
        //solo chi ha creato un evento lo può modificare
        if (!currentUser.getId().equals(found.getOrganizzatore().getId())){
            throw new UnauthorizedException("l'utente corrente non è il creatore dell'evento e non lo può eliminare");
        }
        eventoService.findByIdAndDelete(idEvento);
    }

}
