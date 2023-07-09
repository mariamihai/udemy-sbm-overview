package guru.springframework.brewery.monolith.web.controllers;

import guru.springframework.brewery.monolith.domain.Brewery;
import guru.springframework.brewery.monolith.services.BreweryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/brewery")
@RestController
@RequiredArgsConstructor
public class BreweryController {

    private final BreweryService breweryService;

    @GetMapping
    public @ResponseBody
    List<Brewery> getAllBreweries() {
        return breweryService.getAllBreweries();
    }

    @GetMapping("/{breweryId}")
    public ResponseEntity<Brewery> getBreweryById(@PathVariable("breweryId") UUID breweryId) {
        return new ResponseEntity<>(breweryService.getBreweryById(breweryId), HttpStatus.OK);
    }
}
