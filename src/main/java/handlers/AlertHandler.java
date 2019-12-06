package handlers;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertHandler {
    private WebDriver driver;

    public void acceptAlert(String alertText){
        Alert alert = (new WebDriverWait(driver, 15, 50))
                .until(ExpectedConditions.alertIsPresent());
        if (alert.getText().equals(alertText)){
            alert.accept();
        }
    }

    public void declineAlert(String alertText){
        Alert alert = (new WebDriverWait(driver, 15, 50))
                .until(ExpectedConditions.alertIsPresent());
        if (alert.getText().equals(alertText)){
            alert.dismiss();
        }
    }

    public AlertHandler(WebDriver driver){
        this.driver = driver;
    }
}
