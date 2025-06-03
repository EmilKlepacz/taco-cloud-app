package sia.auth.tacocloudauthserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sia.auth.tacocloudauthserver.service.DataLoaderService;

@SpringBootApplication
public class TacoCloudAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudAuthServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(DataLoaderService dataLoaderService) {
        return args -> dataLoaderService.initData();
    }

}
