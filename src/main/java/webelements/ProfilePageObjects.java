package webelements;

import enums.Constants;
import handlers.WaitHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePageObjects {
    private WebDriver driver;
    private WebDriverWait wait;
    private WaitHandler waitHandler;

    public ProfilePageObjects(WebDriver driver) {
        String profilepageUrl = Constants.PROFILEPAGE_URL.getValue();
        if (!driver.getCurrentUrl().contains(profilepageUrl)) {
            throw new IllegalStateException(
                    "This is not the page you are expected"
            );
        }

        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30, 50);
        waitHandler = new WaitHandler(wait);
        this.driver = driver;
    }

    @FindBy(id = "v-pills-home-tab")
    public WebElement userProfileTab;

    @FindBy(id = "v-pills-messages-tab")
    private WebElement paymentInfoTab;

    @FindBy(id = "firstNameInput")
    private WebElement firstName;

    @FindBy(id = "lastNameInput")
    private WebElement lastName;

    @FindBy(xpath = "//button[contains(text(), 'Save user info')]")
    private WebElement saveUserInfoButton;

    @FindBy(xpath = "//button[contains(text(), 'Save payment info')]")
    private WebElement savePaymentInfoButton;

    @FindBy(id ="successUserInfoSaveInfo")
    private WebElement successFullySavedUserInfo;

    @FindBy(id = "successPaymentInfoSaveInfo")
    private WebElement successFullySavedPaymentInfo;

    @FindBy(xpath = "//div[@class='invalid-feedback' and contains(text(),'Please set your first name.')]")
    private WebElement invalidFirstName;

    @FindBy(xpath = "//div[@class='invalid-feedback' and contains(text(), 'Please set your last name.')]")
    private WebElement invalidLastName;

    @FindBy(xpath = "//div[@class='invalid-feedback' and contains(text(),'Please set your card number.')]")
    private WebElement invalidCardNumber;

    @FindBy(xpath = "//div[@class='invalid-feedback' and contains(text(), 'Please select your payment system.')]")
    private WebElement invalidPaymentSystem;

    @FindBy(id = "cardNumberInput")
    private WebElement cardNumber;

    @FindBy(id = "paymentSystemSelect")
    private WebElement paymentSystemItems;

    @FindBy(id = "paymentRange")
    private WebElement paymentRangeSlider;

    @FindBy(xpath = "//div[contains(text()='Current value: 1')]")
    public WebElement paymentDayValue;

    public void saveFullUserInfo(String name, String surName){
        firstName.click();
        firstName.sendKeys(name);
        lastName.click();
        lastName.sendKeys(surName);
        saveUserInfoButton.click();
    }

    public WebElement waitForSuccessMessage(){
        return waitHandler.elementVisible(successFullySavedUserInfo);
    }

    public WebElement waitForSetFirstNameMessage(){
        return waitHandler.elementVisible(invalidFirstName);
    }

    public WebElement waitForSetLastNameMessage(){
        return waitHandler.elementVisible(invalidLastName);
    }

    public WebElement waitForSuccessfullySavedPaymentInfo(){
        return waitHandler.elementVisible(successFullySavedPaymentInfo);
    }

    public WebElement waitForSetCardNumberMessage(){
        return waitHandler.elementVisible(invalidCardNumber);
    }

    public WebElement waitForSelectPaymentSystemMessage(){
        return waitHandler.elementVisible(invalidPaymentSystem);
    }

    public String getUserDataFromCookies(String cookies) {
        return driver.manage().getCookieNamed(cookies).getValue();
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void fillPaymentInfo(String cardNumberData, String paymentSystem){
        paymentInfoTab.click();
        WaitHandler waitHandler = new WaitHandler(wait);
        waitHandler.elementClickable(cardNumber).click();
        cardNumber.sendKeys(cardNumberData);
        Select paymentSystemsToChoose = new Select(paymentSystemItems);
        paymentSystemsToChoose.selectByVisibleText(paymentSystem);
        waitHandler.elementClickable(paymentRangeSlider).click();
        savePaymentInfoButton.click();
    }
}
