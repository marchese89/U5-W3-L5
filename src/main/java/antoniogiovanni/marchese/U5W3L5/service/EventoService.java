package antoniogiovanni.marchese.U5W3L5.service;

import antoniogiovanni.marchese.U5W3L5.exceptions.NotFoundException;
import antoniogiovanni.marchese.U5W3L5.model.Evento;
import antoniogiovanni.marchese.U5W3L5.model.Utente;
import antoniogiovanni.marchese.U5W3L5.payloads.NewEventoDTO;
import antoniogiovanni.marchese.U5W3L5.repository.EventoRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Evento> getEventi(int page, int size, String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());

        return eventoRepository.findAll(pageable);
    }

    public Evento save(NewEventoDTO eventoDTO,Utente utente) {
        Evento evento = new Evento();
        evento.setTitolo(eventoDTO.titolo());
        evento.setData(eventoDTO.data());
        evento.setLuogo(eventoDTO.luogo());
        evento.setDescrizione(eventoDTO.descrizione());
        evento.setOrganizzatore(utente);
        evento.setPostiDisponibili(eventoDTO.postiDisponibili());
        return eventoRepository.save(evento);
    }

    public Evento findById(UUID id) {
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Evento found = this.findById(id);
        eventoRepository.delete(found);
    }

    public Evento findByIdAndUpdate(UUID id, NewEventoDTO eventoDTO) {
        Evento found = this.findById(id);
        found.setTitolo(eventoDTO.titolo());
        found.setData(eventoDTO.data());
        found.setLuogo(eventoDTO.luogo());
        found.setDescrizione(eventoDTO.descrizione());
        found.setPostiDisponibili(eventoDTO.postiDisponibili());
        return eventoRepository.save(found);
    }
    public Evento uploadImage(UUID eventoId, MultipartFile file)  throws IOException {
        Evento found = this.findById(eventoId);
        String avatarURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImage(avatarURL);
        return eventoRepository.save(found);
    }
}
