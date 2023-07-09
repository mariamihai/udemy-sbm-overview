package guru.springframework.brewery.monolith.services;

import guru.springframework.brewery.monolith.domain.Brewery;

import java.util.List;
import java.util.UUID;

public interface BreweryService {

    List<Brewery> getAllBreweries();

    Brewery getBreweryById(UUID breweryId);
}
