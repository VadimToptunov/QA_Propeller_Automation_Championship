package webelements;

import enums.Constants;
import handlers.ActionHandler;
import handlers.AlertHandler;
import handlers.WaitHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageObjects {

    private WebDriver driver;
    private WebDriverWait wait;
    private WaitHandler waitHandler;
    private ActionHandler actionHandler;

    @FindBy(id = "loginInput")
    private WebElement login;

    @FindBy(id= "passwordInput")
    private WebElement password;

    @FindBy(xpath = "//button[contains(text(), 'Hover me faster!')]")
    private WebElement hoverMeFasterButton;

    @FindBy(linkText = "Wait for some time")
    public WebElement waitForSomeTimeButton;

    @FindBy(xpath = "//img[@src='sign.png']")
    private WebElement signButtonImage;

    @FindBy(xpath = "//h4[contains(text(), 'Welcome to Propeller Championship!')]")
    private WebElement header;

    public  LoginPageObjects(WebDriver driver) {
        String loginpageUrl = Constants.LOGINPAGE_URL.getValue();
        if (!driver.getCurrentUrl().contains(loginpageUrl)) {
            throw new IllegalStateException(
                    "This is not the page you are expected"
            );
        }

        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30, 50);
        waitHandler = new WaitHandler(wait);
        actionHandler = new ActionHandler(driver);
        this.driver = driver;
    }

    private String getTabTitle(){
        return driver.getTitle();
    }

    private void insertLogin(String loginString){
        moveAndClickElement(login);
        login.sendKeys(loginString);
    }

    private void deleteLogin(){
        moveAndClickElement(login);
        login.clear();
    }

    private void deletePassword(){
        moveAndClickElement(password);
        password.clear();
    }

    private void insertPassword(String passwordString){
        moveAndClickElement(password);
        password.sendKeys(passwordString);
    }

    public void clickSignButton(){
        moveAndClickElement(hoverMeFasterButton);
        actionHandler.moveToElement(hoverMeFasterButton);
        waitHandler.elementVisible(signButtonImage).click();
    }

    public boolean assertTitle(){
        String headerText = waitHandler.elementVisible(header).getText();
        return getTabTitle().equals(headerText);
    }

    public void waitThePageIsUploaded(String url){
        wait.until(ExpectedConditions.urlToBe(url));
    }

    private void moveAndClickElement(WebElement element){
        actionHandler.moveToElement(waitHandler.elementVisible(element)).click().perform();
    }

    public void fillLoginFormAndSubmit(String login, String password){
        fillLoginForm(login, password);
        clickSignButton();
    }

    public void fillCredentialsAndDelete(String login, String password){
        fillLoginForm(login, password);
        waitHandler.elementVisible(signButtonImage);
        deleteLogin();
        deletePassword();
    }

    public WebElement hoverMeFasterButton(){
        return waitHandler.elementVisible(hoverMeFasterButton);
    }

    private void fillLoginForm(String login, String password){
        insertLogin(login);
        insertPassword(password);
    }

    public void acceptAlerts(){
        AlertHandler alertHandler = new AlertHandler(driver);
        alertHandler.acceptAlert("Are you sure you want to login?");
        alertHandler.acceptAlert("Really sure?");
    }

    public void acceptAndDeclineAlerts(){
        AlertHandler alertHandler = new AlertHandler(driver);
        alertHandler.acceptAlert("Are you sure you want to login?");
        alertHandler.declineAlert("Really sure?");
    }
}
