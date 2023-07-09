package com.github.sfgbeerworks.sbmbeerinventoryfailover.web.controllers;

import com.github.sfgbeerworks.sbmbeerinventoryfailover.web.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerInventoryFailoverController {

    @GetMapping("/inventory-failover")
    public List<BeerInventoryDto> listBeersById(){
        log.debug("Called listBeersById");

        BeerInventoryDto beerInventoryDto = BeerInventoryDto.builder()
                .id(UUID.randomUUID())
                .beerId(UUID.fromString("00000000-0000-0000-0000-00000000000"))
                .quantityOnHand(999)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

        return List.of(beerInventoryDto);
    }
}
