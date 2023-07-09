package guru.springframework.brewery.monolith.repositories;

import guru.springframework.brewery.monolith.domain.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Beer findByBeerName(String beerName);
}
