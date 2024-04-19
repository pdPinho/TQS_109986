package ua.pt;

import static io.restassured.RestAssured.*;
import static  org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

class fakeAPITest {
    
    private String url = "https://jsonplaceholder.typicode.com/";

    @Test
    public void whenGetAll_thenGetAll(){
        when()
            .get(url + "todos").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test 
    public void whenGetToDo4_thenReturnTitle(){
        when()
            .get(url + "todos/4").
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("title", equalTo("et porro tempora"));
    }

    @Test
    public void whenGetAll_thenReturn198And199ID(){
        when()
            .get(url + "todos").
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("id", hasItems(198, 199));
    }

    @Test
    public void whenGetAll_returnInLessThan2Seconds(){
        when()
            .get(url + "todos").
        then()
            .statusCode(HttpStatus.SC_OK)
            .time(lessThan(2L), TimeUnit.SECONDS);
    }
}
