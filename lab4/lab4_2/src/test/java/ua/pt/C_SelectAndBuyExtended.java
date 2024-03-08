package ua.pt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class C_SelectAndBuyExtended {

    @Test
    @DisplayName("Open website, select a flight and buy it")
    public void testSelectAndBuy(FirefoxDriver driver) {
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