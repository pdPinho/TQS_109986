package ua.pt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {
    private WebDriver driver;

   @FindBy(className = "btn-primary")
   private WebElement purchaseButton;

   @FindBy(tagName="h2")
   private WebElement heading;

   public PurchasePage(WebDriver driver){
       this.driver=driver;
       PageFactory.initElements(driver, this);
   }

    public void clickPurchaseFlight(){
        purchaseButton.click();
    }

    public boolean isOpen(){
        return heading.getText().toString().contains("Your flight from TLV to SFO has been reserved.");
    }
}
