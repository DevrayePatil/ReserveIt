package org.example.reserveit.repositories;

import org.example.reserveit.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    boolean existsByAddressAndName(String address, String name);
}
