package guru.springframework.brewery.monolith.repositories;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.domain.BeerInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {

    List<BeerInventory> findAllByBeer(Beer beer);
}
