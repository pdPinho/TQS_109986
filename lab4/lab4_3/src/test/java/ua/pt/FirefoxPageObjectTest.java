package ua.pt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class FirefoxPageObjectTest {

    @Test
    @DisplayName("Open website, select a flight and buy it")
    public void testSelectAndBuy(FirefoxDriver driver) {
        HomePage home = new HomePage(driver);
        driver.manage().window().setSize(new Dimension(550, 691));

        home.setOrigin("Boston");
        home.setDestination("London");
        home.clickFindFlights();

        ReservePage reservePage = new ReservePage(driver);
        assertTrue(reservePage.isOpen());

        reservePage.clickChooseFlight();

        PurchasePage purchasePage = new PurchasePage(driver);
        assertTrue(purchasePage.isOpen());

        purchasePage.clickPurchaseFlight();
        assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }
    
}