package app.milanpizza.menucatalog.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
//@Testcontainers
public class TestConfig {

//    @Container
//    private static final MongoDBContainer mongoDBContainer =
//            new MongoDBContainer("mongo:5.0")
//                    .withExposedPorts(27017);

    @LocalServerPort
    protected int port;

//    @DynamicPropertySource
//    static void mongoProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//    }

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }
}