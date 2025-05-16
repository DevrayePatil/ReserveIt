package org.example.reserveit.services.impl;

import lombok.AllArgsConstructor;
import org.example.reserveit.exceptions.AttributeNotFoundException;
import org.example.reserveit.exceptions.DuplicateEntityException;
import org.example.reserveit.exceptions.TheatreNotFoundException;
import org.example.reserveit.models.Theatre;
import org.example.reserveit.repositories.TheatreRepository;
import org.example.reserveit.services.TheatreService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TheatreServiceImpl implements TheatreService {


    private final TheatreRepository theatreRepository;

    @Override
    public Theatre addTheatre(String name, String address) {

        if (theatreRepository.existsByAddressAndName(address, name)) {
            throw new DuplicateEntityException("Theatre already present by same name and address");
        }

        if (name == null || name.isEmpty()) {
            throw new AttributeNotFoundException("Theatre name");
        }

        if (address == null) {
            throw new AttributeNotFoundException("Theatre Address");
        }

        Theatre theatre = new Theatre();
        theatre.setName(name);
        theatre.setAddress(address);

        return theatreRepository.save(theatre);
    }

    @Override
    public Theatre getTheatre(Long id) throws TheatreNotFoundException {

        return theatreRepository.findById(id).orElseThrow(TheatreNotFoundException::new);
    }

    @Override
    public Theatre updateTheatre(Long id, String name, String address) throws TheatreNotFoundException {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(TheatreNotFoundException::new);

        if (name != null) {
            theatre.setName(name);
        }
        if (address != null) {
            theatre.setAddress(address);
        }
        return theatreRepository.save(theatre);
    }

    @Override
    public Theatre deleteTheatre(Long id) throws TheatreNotFoundException {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(TheatreNotFoundException::new);

        theatreRepository.delete(theatre);

        return theatre;
    }

    @Override
    public List<Theatre> getTheatres() {
        return theatreRepository.findAll();
    }
}
