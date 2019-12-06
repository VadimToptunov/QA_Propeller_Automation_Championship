package handlers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHandler {
    private WebDriverWait wait;

    public WebElement elementClickable(WebElement element){
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement elementVisible(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WaitHandler(WebDriverWait wait){
        this.wait = wait;
    }
}
