package org.example.reserveit.repositories;

import org.example.reserveit.models.Screen;
import org.example.reserveit.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findAllByScreen(Screen screen);
}
