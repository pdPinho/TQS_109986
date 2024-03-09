package ua.pt;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private WebDriver driver;

   //Page URL
   private static String PAGE_URL="https://blazedemo.com/";

   @FindBy(name="fromPort")
   private WebElement originDropdown;

   @FindBy(name="toPort")
   private WebElement destinationDropdown;

   @FindBy(className = "btn-primary")
   private WebElement flightsButton;

   //Constructor
   public HomePage(WebDriver driver){
       this.driver=driver;
       driver.get(PAGE_URL);

       //Initialise Elements
       PageFactory.initElements(driver, this);
   }

    public void setOrigin(String origin){
        new Select(originDropdown).selectByVisibleText(origin);
    }

    public void setDestination(String destination){
        new Select(destinationDropdown).selectByVisibleText(destination);
    }

    public void clickFindFlights(){
        flightsButton.click();
    }
}
