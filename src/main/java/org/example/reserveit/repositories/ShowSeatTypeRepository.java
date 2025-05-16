package org.example.reserveit.repositories;

import org.example.reserveit.models.Show;
import org.example.reserveit.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    void deleteAllByShow(Show show);

    List<ShowSeatType> findAllByShow(Show show);
}
