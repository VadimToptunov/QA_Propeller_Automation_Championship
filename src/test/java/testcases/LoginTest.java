package testcases;

import enums.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import webelements.LoginPageObjects;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertThat;

/**
 * @Author: Vadim Toptunov
 * @Error description:
 *      <li>1. Log In page title is incorrect - Welcom to Propeller Automated Testing Championship
 *      It should comply with <h4>Welcome to Propeller Championship!</h4></li>
 *      <li>2. If a user tries to log in with a wrong login/password pair it is not redirected to Error page.</li>
 *      <li>3. Login is thought to be an e-mail, but it easily gets a string like "test". </li>
 *      <li>4. If a user inserts credentials and then deletes them, Sign In button is still shown.</li>
 */

@RunWith(Parameterized.class)
public class LoginTest {
    private WebDriver driver;
    private LoginPageObjects loginPageObjects;
    private String login = Constants.LOGIN.getValue();
    private String password = Constants.PASSWORD.getValue();
    private String errorpageUrl = Constants.ERRORPAGE_URL.getValue();
    private String mainpageUrl = Constants.MAINPAGE_URL.getValue();

    @Parameterized.Parameter
    public Class<? extends WebDriver> driverClass;

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { ChromeDriver.class }, { FirefoxDriver.class }});
    }

    @Before
    public void setupTest() throws Exception {
        WebDriverManager.getInstance(driverClass).setup();
        driver = driverClass.newInstance();
        driver.get(Constants.LOGINPAGE_URL.getValue());
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void assertTitleEqualsHeaderTest(){
        loginPageObjects =  new LoginPageObjects(driver);
        assertTrue(loginPageObjects.assertTitle());
    }

    @Test
    public void successfulAuthorizationTest() {
        loginPageObjects =  new LoginPageObjects(driver);
        loginPageObjects.fillLoginFormAndSubmit(login, password);
        loginPageObjects.acceptAlerts();
        loginPageObjects.waitThePageIsUploaded(mainpageUrl);
        assertEquals(mainpageUrl, driver.getCurrentUrl());
    }

    @Test
    public void failedAuthorizationTest(){
        loginPageObjects =  new LoginPageObjects(driver);
        loginPageObjects.fillLoginFormAndSubmit(login, password);
        loginPageObjects.acceptAndDeclineAlerts();
        loginPageObjects.waitThePageIsUploaded(errorpageUrl);
        assertEquals(errorpageUrl, driver.getCurrentUrl());
    }

    @Test
    public void falseAuthorizationAttemptTest(){
        loginPageObjects =  new LoginPageObjects(driver);
        loginPageObjects.fillLoginFormAndSubmit("__admin__@email.com", "*");
        loginPageObjects.acceptAlerts();
        assertEquals(errorpageUrl, driver.getCurrentUrl());
    }

    @Test
    public void deleteAuthorizationTest(){
        loginPageObjects.fillCredentialsAndDelete(login, password);
        assertTrue(loginPageObjects.hoverMeFasterButton().isDisplayed());
    }

    @Test
    public void signInWithNoCredentials(){
        loginPageObjects.fillCredentialsAndDelete(login, password);
        loginPageObjects.clickSignButton();
        assertThat(mainpageUrl, CoreMatchers.not(String.valueOf(equals(driver.getCurrentUrl()))));
    }

}

