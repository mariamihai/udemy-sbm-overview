package guru.springframework.brewery.monolith.config;

import org.springframework.stereotype.Component;

@Component
public class WebConfig {

    private WebConfig() {
    }

    public static final String BEER_PATH_V1 = "/api/v1/beer/";
    public static final String BREWERY_PATH_V1 = "/api/v1/brewery";

}
