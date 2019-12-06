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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @Author: Vadim Toptunov
 * @Error description:
 * <li>1.If a user adds an article to saved, the item disappears from menu. </li>
 */

@RunWith(Parameterized.class)
public class MainPageTest {
    private WebDriver driver;
    private String login = Constants.LOGIN.getValue();
    private String password = Constants.PASSWORD.getValue();
    private String mainPageUrl = Constants.MAINPAGE_URL.getValue();
    private static final String SAVED_COOKIES = "saved";
    private static final String ADIDAS = "Adidas";
    private static final String JON_SNOW = "Jon Snow";
    private static final String YOUTUBE = "Youtube";


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
        LoginPageObjects loginPageObjects = new LoginPageObjects(driver);
        loginPageObjects.fillLoginFormAndSubmit(login, password);
        loginPageObjects.acceptAlerts();
        loginPageObjects.waitThePageIsUploaded(mainPageUrl);
    }

    @After
    public void teardown() {
        if (driver != null) {
            MainPageObjects mainPageObjects = new MainPageObjects(driver);
            mainPageObjects.deleteAllCookies();
            driver.quit();
        }
    }

    @Test
    public void checkNotSavedOpenedCookiesTest(){
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.clickTopButtons();
        List<String> notSavedOpenedCookies = mainPageObjects.getDataCookies("notSavedOpened");
        assertTrue(notSavedOpenedCookies.contains("Advertisers"));
        assertTrue(notSavedOpenedCookies.contains("Publishers"));
        assertTrue(notSavedOpenedCookies.contains("Top level clients"));
    }


    @Test
    public void jonSnowAddArticleTest(){
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.addArticle(mainPageObjects.topLevelClientsButton, mainPageObjects.jonSnowButton);
        List<String> savedCookies = mainPageObjects.getDataCookies(SAVED_COOKIES);
        assertTrue(savedCookies.contains(JON_SNOW));
        assertTrue(mainPageObjects.savedArticlesCard().isDisplayed());
    }

    @Test
    public void jonSnowRemoveArticleTest(){
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.removeArticle(mainPageObjects.topLevelClientsButton, mainPageObjects.jonSnowButton);
        assertFalse(mainPageObjects.checkCookiesContains(SAVED_COOKIES));
        assertFalse(mainPageObjects.savedArticlesCard.isDisplayed());
    }

    @Test
    public void allTypesArticlesAdd(){
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.addArticle(mainPageObjects.topLevelClientsButton, mainPageObjects.jonSnowButton);
        mainPageObjects.addArticle(mainPageObjects.advertisersButton, mainPageObjects.adidasButton);
        mainPageObjects.addArticle(mainPageObjects.publishersButton, mainPageObjects.youtubeButton);
        List<String> savedCookies = mainPageObjects.getDataCookies(SAVED_COOKIES);
        assertTrue(savedCookies.contains(JON_SNOW));
        assertTrue(savedCookies.contains(ADIDAS));
        assertTrue(savedCookies.contains(YOUTUBE));
        assertTrue(mainPageObjects.savedArticlesCard().isDisplayed());
    }

    @Test
    public void heroImageSizeTest(){
        MainPageObjects mainPageObjects = new MainPageObjects(driver);
        mainPageObjects.addArticle(mainPageObjects.topLevelClientsButton, mainPageObjects.jonSnowButton);
        assertTrue(mainPageObjects.assertHeroImage());
    }
}