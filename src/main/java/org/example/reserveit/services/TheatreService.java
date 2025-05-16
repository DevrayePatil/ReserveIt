package org.example.reserveit.services;

import org.example.reserveit.exceptions.TheatreNotFoundException;
import org.example.reserveit.models.Theatre;

import java.util.List;

public interface TheatreService {
    Theatre addTheatre(String name, String address);
    Theatre getTheatre(Long id) throws TheatreNotFoundException;
    Theatre updateTheatre(Long id, String name, String address) throws TheatreNotFoundException;
    Theatre deleteTheatre(Long id) throws TheatreNotFoundException;
    List<Theatre> getTheatres();
}
