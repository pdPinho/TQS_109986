package ua.pt;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

class HelloWorldFirefoxTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void test() {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }


    @Test
    public void testSelectAndBuy() {
      driver.get("https://blazedemo.com/");
      driver.manage().window().setSize(new Dimension(550, 691));

      driver.findElement(By.name("fromPort")).click();
      {
        WebElement dropdown = driver.findElement(By.name("fromPort"));
        dropdown.findElement(By.xpath("//option[. = 'Boston']")).click();
      }

      driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(3)")).click();

      driver.findElement(By.name("toPort")).click();
      {
        WebElement dropdown = driver.findElement(By.name("toPort"));
        dropdown.findElement(By.xpath("//option[. = 'London']")).click();
      }

      driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
      driver.findElement(By.cssSelector(".btn-primary")).click();
      driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
      driver.findElement(By.cssSelector(".btn-primary")).click();
      
      assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }

}