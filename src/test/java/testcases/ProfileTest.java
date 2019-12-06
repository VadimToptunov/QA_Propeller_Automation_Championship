package testcases;

import enums.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import webelements.LoginPageObjects;
import webelements.MainPageObjects;
import webelements.ProfilePageObjects;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @Author: Vadim Toptunov
 * @Error description:
 * <li>1. If a user inserts no First Name and no Last name, there's only one error message ("Please set your first name.")
 * instead of a separate error message for both fields.</h4></li>
 * <li>2. If a user sets no payment data, i.e. no card number and no payment system, but tries to save it,
 * there's only one error message (Please set your card number), instead of a separate error message for both fields.</li>
 * <li>3. Card number accepts all kinds of input, even the invalid one, like ""@##$@$". It should not."</li>
 */

@RunWith(Parameterized.class)
public class ProfileTest {
    private WebDriver driver;
    private LoginPageObjects loginPageObjects;
    private String login = Constants.LOGIN.getValue();
    private String password = Constants.PASSWORD.getValue();
    private String mainPageUrl = Constants.MAINPAGE_URL.getValue();
    private String profilePageUrl = Constants.PROFILEPAGE_URL.getValue();
    private String firstName = Constants.FIRSTNAME.getValue();
    private String lastName = Constants.LASTNAME.getValue();
    private String empty = Constants.EMPTY.getValue();
    private String cardNumber = Constants.CARDNUMBER.getValue();
    private String visaCard = Constants.PAYMENT_METHOD_VISA.getValue();
    private String invalidInputForCardNumber = Constants.INVALID_VALUE.getValue();

    @Parameterized.Parameter
    public Class<? extends WebDriver> driverClass;

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{ChromeDriver.class}, {FirefoxDriver.class}});
    }

    @Before
    public void setupTest() throws Exception {
        WebDriverManager.getInstance(driverClass).setup();
        driver = driverClass.newInstance();
        driver.get(Constants.LOGINPAGE_URL.getValue());
        loginPageObjects = new LoginPageObjects(driver);
        loginPageObjects.fillLoginFormAndSubmit(login, password);
        loginPageObjects.acceptAlerts();
        loginPageObjects.waitThePageIsUploaded(mainPageUrl);
    }

    @After
    public void teardown() {
        if (driver != null) {
            ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
            profilePageObjects.deleteAllCookies();
            driver.quit();
        }
    }

    @Test
    public void getToProfilePage() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
    }

    @Test
    public void fillFullNameTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.saveFullUserInfo(firstName, lastName);
        assertTrue(profilePageObjects.waitForSuccessMessage().isDisplayed());
        assertEquals(firstName, profilePageObjects.getUserDataFromCookies("firstName"));
        assertEquals(lastName, profilePageObjects.getUserDataFromCookies("lastName"));
    }

    @Test
    public void invalidFirstNameTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.saveFullUserInfo(empty, lastName);
        assertTrue(profilePageObjects.waitForSetFirstNameMessage().isDisplayed());
    }

    @Test
    public void invalidLastNameTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.saveFullUserInfo(firstName, empty);
        assertTrue(profilePageObjects.waitForSetLastNameMessage().isDisplayed());
    }

    @Test
    public void noNameSetTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.saveFullUserInfo(empty, empty);
        assertTrue(profilePageObjects.waitForSetFirstNameMessage().isDisplayed());
    }

    @Test
    public void fillPaymentInfoTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.fillPaymentInfo(cardNumber, visaCard);
        assertTrue(profilePageObjects.waitForSuccessfullySavedPaymentInfo().isDisplayed());
    }

    @Test
    public void noCardNumberTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.fillPaymentInfo(empty, visaCard);
        assertTrue(profilePageObjects.waitForSetCardNumberMessage().isDisplayed());
    }

    @Test
    public void noPaymentInfoTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.fillPaymentInfo(cardNumber, empty);
        assertTrue(profilePageObjects.waitForSelectPaymentSystemMessage().isDisplayed());
    }

    @Test
    public void noPaymentDataSetTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.fillPaymentInfo(empty, empty);
        assertTrue(profilePageObjects.waitForSetCardNumberMessage().isDisplayed());
    }

    @Test
    public void cardNumberOfWildCardsTest() {
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickOnAvatar();
        loginPageObjects.waitThePageIsUploaded(profilePageUrl);
        assertEquals(profilePageUrl, driver.getCurrentUrl());
        ProfilePageObjects profilePageObjects = new ProfilePageObjects(driver);
        profilePageObjects.fillPaymentInfo(invalidInputForCardNumber, visaCard);
        assertTrue(profilePageObjects.waitForSetCardNumberMessage().isDisplayed());
    }
}
