// Generated by Selenium IDE
package ua.pt.FunctionalTests;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class CheckFunctionalityTest {

  @Test
  @DisplayName("Open website, select a trip, reserve it, pay it and check user reservations")
  void checkFunctionality(FirefoxDriver driver) {
    // setting settings
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("--headless");
    driver = new FirefoxDriver(options);

    // setting up workspace
    driver.get("http://localhost:3000/");
    driver.manage().window().setSize(new Dimension(599, 694));

    // select origin
    driver.findElement(By.id("origin")).click();
    {
      WebElement dropdown = driver.findElement(By.id("origin"));
      dropdown.findElement(By.xpath("//option[. = 'Aveiro']")).click();
    }
    driver.findElement(By.cssSelector("#origin > option:nth-child(2)")).click();

    // select destination
    driver.findElement(By.id("destination")).click();
    {
      WebElement dropdown = driver.findElement(By.id("destination"));
      dropdown.findElement(By.xpath("//option[. = 'Coimbra']")).click();
    }
    driver.findElement(By.cssSelector("#destination > option:nth-child(2)")).click();

    // select date
    driver.findElement(By.cssSelector(".border-2")).click();
    driver.findElement(By.cssSelector(".react-datepicker__day--021")).click();

    // submit
    driver.findElement(By.id("submit")).click();

    // select trip
    driver.findElement(By.cssSelector(".border-t:nth-child(1) .rounded-md")).click();

    // provide information
    driver.findElement(By.id("inputName")).click();
    driver.findElement(By.id("inputName")).sendKeys("Pedro");
    driver.findElement(By.id("address")).click();
    driver.findElement(By.id("address")).sendKeys("123123");

    // select currency
    driver.findElement(By.id("currency")).click();
    {
      WebElement dropdown = driver.findElement(By.id("currency"));
      dropdown.findElement(By.xpath("//option[. = 'EUR']")).click();
    }
    driver.findElement(By.cssSelector("option:nth-child(2)")).click();

    // reserve trip
    driver.findElement(By.cssSelector(".bg-blue-500")).click();

    // pay trip
    driver.findElement(By.cssSelector(".bg-blue-500")).click();

    // check user reservations
    driver.findElement(By.cssSelector("a:nth-child(2) > .ml-2")).click();
    String paid = driver.findElement(By.id("reservation-1")).getText();

    assertThat(paid).isEqualTo("paid");
  }
}
