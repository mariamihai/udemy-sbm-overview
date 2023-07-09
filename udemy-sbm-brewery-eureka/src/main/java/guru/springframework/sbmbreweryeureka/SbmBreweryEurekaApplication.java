package guru.springframework.sbmbreweryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SbmBreweryEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbmBreweryEurekaApplication.class, args);
    }

}
