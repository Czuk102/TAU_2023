package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginSteps {
    private final Logger logger = Logger.getLogger(LoginSteps.class.getName());

    private static WebDriver driver;

    @After
    public static void tearDown() {
        if (driver != null){
            driver.quit();
        }
    }
    @Given("I open the browser {string}")
    public void i_open_the_browser(String browser) {
        if (browser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
    }
    @Given("I am on the saucedemo.com login page")
    public void i_am_on_the_saucedemo_com_login_page() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("I enter username {string} in the login field")
    public void i_enter_in_the_login_field(String username) {
        WebElement usernameTextbox = driver.findElement(By.id("user-name"));
        usernameTextbox.sendKeys(username);
    }

    @When("I enter password {string} in the password field")
    public void i_enter_in_the_password_field(String password){
        WebElement passwordTextbox = driver.findElement(By.id("password"));
        passwordTextbox.sendKeys(password);
    }

    @When("I click the {string} button")
    public void i_click_the_button(String string) {
        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();
    }

    @Then("the browser path should confirm a successful login")
    public void the_browser_path_should_confirm_a_successful_login() {
        assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }



}
