package guru.springframework.sbmbeerinventoryservice.bootstrap;

import guru.springframework.sbmbeerinventoryservice.domain.BeerInventory;
import guru.springframework.sbmbeerinventoryservice.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
//@Component
public class BeerInventoryLoader implements CommandLineRunner {

    private static final String BEER_1_UPC = "0631234200036";
    private static final String BEER_2_UPC = "0631234300019";
    private static final String BEER_3_UPC = "0083783375213";

    private static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    private static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    private static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public void run(String... args) {
        if(beerInventoryRepository.count() == 0) {
            loadInitialObjects();
        }
    }

    private void loadInitialObjects() {
        beerInventoryRepository.save(
                BeerInventory.builder()
                             .beerId(BEER_1_UUID)
                             .upc(BEER_1_UPC)
                             .quantityOnHand(50)
                             .build());

        beerInventoryRepository.save(
                BeerInventory.builder()
                        .beerId(BEER_2_UUID)
                        .upc(BEER_2_UPC)
                        .quantityOnHand(50)
                        .build());

        beerInventoryRepository.saveAndFlush(
                BeerInventory.builder()
                        .beerId(BEER_3_UUID)
                        .upc(BEER_3_UPC)
                        .quantityOnHand(50)
                        .build());

        log.debug("Added to the inventory. Current count: " + beerInventoryRepository.count());
    }
}
