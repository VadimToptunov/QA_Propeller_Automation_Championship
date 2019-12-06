package webelements;

import enums.Constants;
import handlers.ActionHandler;
import handlers.WaitHandler;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

public class MainPageObjects {
    private WebDriver driver;
    private WebDriverWait wait;
    private WaitHandler waitHandler;
    private ActionHandler actionHandler;

    public  MainPageObjects(WebDriver driver) {
        String mainpageUrl = Constants.MAINPAGE_URL.getValue();
        if (!driver.getCurrentUrl().contains(mainpageUrl)) {
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

    @FindBy(id = "avatar")
    private WebElement avatar;

    @FindBy(id = "heroImage")
    private WebElement heroImage;

    @FindBy(className = "form-control")
    private WebElement textToScroll;

    @FindBy(xpath = "//button[contains(text(), 'Advertisers')]")
    public WebElement advertisersButton;

    @FindBy(xpath = "//button[contains(text(), 'Publishers')]")
    public WebElement publishersButton;

    @FindBy(xpath = "//button[contains(text(), 'Top level clients')]")
    public WebElement topLevelClientsButton;

    @FindBy(xpath = "//button[contains(text(), 'Move to saved')]")
    private WebElement moveToSavedButton;

    @FindBy(xpath = "//button[contains(text(), 'Removed from saved')]")
    private WebElement removeFromSavedButton;

    @FindBy(xpath = "//div[contains(text(), 'Saved articles')]")
    public WebElement savedArticlesCard;

    @FindBy(xpath = "//div[@class='card-body text-right']//*[@type='button' and contains(text(), 'Top level clients')]")
    public WebElement topLevelClientsButtonAdded;

    @FindBy(xpath = "//button[contains(text(), 'Jon Snow')]")
    public WebElement jonSnowButton;

    @FindBy(xpath = "//button[contains(text(), 'Adidas')]")
    public WebElement adidasButton;

    @FindBy(xpath = "//button[contains(text(), 'Youtube')]")
    public WebElement youtubeButton;

    @FindBy(xpath = "//div[contains(@class, 'ui-slider ui-corner-all ui-slider-horizontal ui-widget ui-widget-content')]/span[1]")
    private WebElement heroImageSlider;


    private void scrollDownTextArea(){
        waitHandler.elementVisible(textToScroll).click();
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", textToScroll);
        executor.executeScript(String.format("window.scrollTo(0, %d);", driver.manage().window().getSize().getHeight()), "");
    }

    private void scrollDownPage(){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript(String.format("window.scrollTo(0, %d);", driver.manage().window().getSize().getHeight()), "");
    }

    public void clickOnAvatar(){
        waitHandler.elementVisible(avatar).click();
    }

    public List<String> getDataCookies(String neededCookieName) {
        return Arrays.asList(driver.manage().getCookieNamed(neededCookieName).getValue().split(","));
    }

    public boolean checkCookiesContains(String cookieName){
        return driver.manage().getCookies().toString().contains(cookieName);
    }

    public WebElement savedArticlesCard(){
        return waitHandler.elementVisible(savedArticlesCard);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    private void maximizeWindow(){
        driver.manage().window().maximize();
    }

    private int getHeroImageSize(){
        return heroImage.getSize().getHeight();
    }

    public boolean assertHeroImage(){
        int initialSize = getHeroImageSize();
        scrollLeft();
        int anotherSize = getHeroImageSize();
        return anotherSize > initialSize;
    }

    private void scrollLeft(){
        actionHandler.dragAndDrop(heroImageSlider).build().perform();
    }

    public void clickTopButtons(){
        maximizeWindow();
        waitHandler.elementClickable(advertisersButton).click();
        waitHandler.elementClickable(publishersButton).click();
        waitHandler.elementClickable(topLevelClientsButton).click();
    }

    public void addArticle(WebElement mainButton, WebElement subButton){
        maximizeWindow();
        waitHandler.elementClickable(mainButton).click();
        waitHandler.elementVisible(subButton).click();
        scrollDownTextArea();
        scrollDownPage();
        waitHandler.elementVisible(moveToSavedButton).click();
        scrollDownPage();
        waitHandler.elementVisible(savedArticlesCard);
    }

    public void removeArticle(WebElement mainButton, WebElement subButton){
        maximizeWindow();
        waitHandler.elementClickable(mainButton).click();
        waitHandler.elementVisible(subButton).click();
        scrollDownTextArea();
        waitHandler.elementClickable(moveToSavedButton).click();
        scrollDownPage();
        waitHandler.elementClickable(removeFromSavedButton).click();
    }
}
