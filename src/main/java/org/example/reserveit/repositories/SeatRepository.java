package org.example.reserveit.repositories;

import org.example.reserveit.models.Screen;
import org.example.reserveit.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreen(Screen screen);
}
