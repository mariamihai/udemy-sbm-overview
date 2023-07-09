package guru.springframework.brewery.monolith.repositories;

import guru.springframework.brewery.monolith.domain.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BreweryRepository extends JpaRepository<Brewery, UUID> {
}
