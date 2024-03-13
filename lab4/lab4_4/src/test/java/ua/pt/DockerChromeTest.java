package ua.pt;

import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

// Just the basic test provided by the professor's link 
// to check how it is that docker works like

@ExtendWith(SeleniumJupiter.class)
class DockerChromeTest {

    @Test
    void testChrome(@DockerBrowser(type = CHROME, version = "beta", args = "--disable-gpu,--no-sandbox") WebDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}