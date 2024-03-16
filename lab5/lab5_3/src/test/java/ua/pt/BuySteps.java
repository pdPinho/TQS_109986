package ua.pt;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BuySteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
        driver.manage().window().setSize(new Dimension(550, 691));
    }

    @And("I choose {string} as my origin and {string} as my destination")
    public void iLogin(String origin, String destination) {

        WebElement dropdownOrigin = driver.findElement(By.name("fromPort"));
        dropdownOrigin.findElement(By.xpath("//option[. = '" + origin + "']")).click();

        WebElement dropdownDestination = driver.findElement(By.name("toPort"));
        dropdownDestination.findElement(By.xpath("//option[. = '" + destination + "']")).click();

    }

    @And("I click Find Flights")
    public void findFlights() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I should be redirected to {string}")
    public void iShouldSee(String url) {
        assertThat(driver.getCurrentUrl()).isEqualTo(url);
    }

    @And("I click Choose This Flight")
    public void chooseFlight() {
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
    }

    @And("I click Purchase Flight")
    public void purchaseFlight() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @And("be presented with the title {string}")
    public void flightBought(String title) {
        assertThat(driver.getTitle()).isEqualTo(title);
    }
}