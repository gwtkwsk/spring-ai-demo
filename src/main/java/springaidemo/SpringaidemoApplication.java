package springaidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EtlProperties.class)
public class SpringaidemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringaidemoApplication.class, args);
    }
}
