package ua.pt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReservePage {
    private WebDriver driver;

   @FindBy(className = "btn-small")
   private WebElement flightButton;

   @FindBy(tagName="h3")
   private WebElement heading;

   public ReservePage(WebDriver driver){
       this.driver=driver;
       PageFactory.initElements(driver, this);
   }

    public void clickChooseFlight(){
        flightButton.click();
    }

    public boolean isOpen(){
        return heading.getText().toString().contains("Flights from Boston to London:");
    }
}
