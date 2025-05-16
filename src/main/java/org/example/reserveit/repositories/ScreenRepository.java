package org.example.reserveit.repositories;

import org.example.reserveit.models.Screen;
import org.example.reserveit.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen, Long> {


    List<Screen> findAllByTheatre(Theatre theatre);

    boolean existsScreenByNameAndTheatre(String name, Theatre theatre);
}
