package ua.pt.lab7_4;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.apache.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
class appTests {

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                                                            .withUsername("username")
                                                            .withPassword("password")
                                                            .withDatabaseName("test");

    @LocalServerPort
    int randomServerPort;

    @Autowired
    CarRepository carRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    void whenGetCarById_thenApiReturnsOneCar() {
        Car car = new Car("maker", "model");
        carRepository.save(car);

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "cars", String.valueOf(car.getCarId()) )
                .build()
                .toUriString();

        when()
            .get(endpoint).
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("maker", equalTo("maker"));
    }

                                                            
}
