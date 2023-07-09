package guru.springframework.sbmrestdocs.bootstrap;

import guru.springframework.sbmrestdocs.domain.Beer;
import guru.springframework.sbmrestdocs.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                    .id(UUID.fromString("680334ca-5023-4ea9-ae81-ecb6f765eeaa"))
                    .beerName("Beer 1")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(1234567890L)
                    .price(new BigDecimal("11.00"))
                    .build());

            beerRepository.save(Beer.builder()
                    .id(UUID.fromString("bbd59dfd-2c30-4ba9-b499-f48b3fd6b2b8"))
                    .beerName("Beer 2")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(2134567890L)
                    .price(new BigDecimal("12.00"))
                    .build());
        }
    }


}