package guru.springframework.brewery.monolith.services;

import guru.springframework.brewery.monolith.domain.Brewery;
import guru.springframework.brewery.monolith.repositories.BreweryRepository;
import guru.springframework.brewery.monolith.web.controllers.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BreweryServiceImpl implements BreweryService {

    private final BreweryRepository breweryRepository;

    @Override
    public List<Brewery> getAllBreweries() {
        return breweryRepository.findAll();
    }

    @Override
    public Brewery getBreweryById(UUID breweryId) {
        return breweryRepository.findById(breweryId).orElseThrow(NotFoundException::new);
    }
}
