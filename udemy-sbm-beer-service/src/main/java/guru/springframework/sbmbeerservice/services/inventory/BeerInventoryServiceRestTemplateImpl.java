package guru.springframework.sbmbeerservice.services.inventory;

import guru.springframework.sbmbeerservice.web.model.events.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Profile("!local-discovery")
@ConfigurationProperties(prefix = "sbm.brewery")
@Component
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    private String beerInventoryServiceHost;

    private final RestTemplate restTemplate;

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder,
                                                @Value("${sbm.brewery.inventory-user}") String inventoryUser,
                                                @Value("${sbm.brewery.inventory-password}") String inventoryPassword) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication(inventoryUser, inventoryPassword)
                .build();
    }

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    @Override
    public Optional<Integer> getOnHandInventory(UUID beerId) {
        log.debug("Calling Inventory Service for " + beerId);

        if(isValidConnection()) {
            ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate
                    .exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<BeerInventoryDto>>(){}, (Object) beerId);

            return Optional.of(Objects.requireNonNull(responseEntity.getBody())
                    .stream()
                    .mapToInt(BeerInventoryDto::getQuantityOnHand)
                    .sum());
        }

        return Optional.empty();
    }

    private boolean isValidConnection() {
        try {
            Socket clientSocket =
                    new Socket(beerInventoryServiceHost.split(":")[1].replace("//", ""),
                               Integer.parseInt(beerInventoryServiceHost.split(":")[2]));
            clientSocket.close();
        } catch (Exception e) {
            log.error("Cannot connect to the Inventory Service");
            // TODO: uncomment
//            log.error("Cannot connect to the Inventory Service", e);
            return false;
        }

        return true;
    }
}
