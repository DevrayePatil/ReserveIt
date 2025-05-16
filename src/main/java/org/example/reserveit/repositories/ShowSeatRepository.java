package org.example.reserveit.repositories;

import org.example.reserveit.models.Show;
import org.example.reserveit.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    void deleteAllByShow(Show show);

    List<ShowSeat> findAllByShow(Show show);
}
