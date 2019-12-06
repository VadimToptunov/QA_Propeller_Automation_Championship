package handlers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionHandler {
    WebDriver driver;

    public Actions moveToElement(WebElement element){
        Actions builder = new Actions(driver);
        return builder.moveToElement(element);
    }

    public Actions dragAndDrop(WebElement element){
        Actions builder = new Actions(driver);
        return builder.dragAndDropBy(element, 300, 0);
    }

    public ActionHandler(WebDriver driver){
        this.driver = driver;
    }
}