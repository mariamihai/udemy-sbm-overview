package guru.springframework.sbmconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SbmConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbmConfigServerApplication.class, args);
    }

}
