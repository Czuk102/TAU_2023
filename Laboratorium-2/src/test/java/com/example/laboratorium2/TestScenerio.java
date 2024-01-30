package com.example.laboratorium2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScenerio {
    private WebDriver driver;
    private final Logger logger = Logger.getLogger(TestScenerio.class.getName());

    private static Stream<WebDriver> provideWebDrivers() {
        return Stream.of(
                WebDriverManager.firefoxdriver().create(),
                WebDriverManager.chromedriver().create());
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    void shouldSuccessfullyLoginAndReachInventorySite(WebDriver driver) {

        this.driver = driver;
        logger.info("Test started");
        logger.info("Opening browser");
        driver.get("https://www.saucedemo.com/");
        WebElement usernameTextbox = driver.findElement(By.id("user-name"));
        usernameTextbox.sendKeys("standard_user");
        WebElement passwordTextbox = driver.findElement(By.id("password"));
        passwordTextbox.sendKeys("secret_sauce");
        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();
        assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
        logger.info("Test finished");
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    public void testLoginAndAddToCart(WebDriver driver) {
        this.driver = driver;
        logger.info("Test started with WebDriver: " + driver.getClass().getSimpleName());

        logger.info("Opening browser and navigating to SauceDemo website");
        driver.get("https://www.saucedemo.com/");

        logger.info("Locating the username textbox");
        WebElement usernameTextbox = driver.findElement(By.id("user-name"));
        usernameTextbox.sendKeys("standard_user");

        logger.info("Locating the password textbox");
        WebElement passwordTextbox = driver.findElement(By.id("password"));
        passwordTextbox.sendKeys("secret_sauce");

        logger.info("Locating the login button and attempting to log in");
        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();

        logger.info("Checking if the login was successful by comparing current URL");
        assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
        String productsPageUrl = driver.getCurrentUrl();
        assertTrue(productsPageUrl.contains("/inventory.html"));
        logger.info("Login successful, landed on products page");

        logger.info("Locating 'Add to Cart' button for Sauce Labs Backpack");
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCartButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        logger.info("Checking if product has been added to the cart");
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("1", cartBadge.getText());
        logger.info("Product added to cart successfully");

        logger.info("Test completed");
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    public void testLoginAndAddBookToCollection(WebDriver driver) {
        this.driver = driver;
        logger.info("Test started with WebDriver: " + driver.getClass().getSimpleName());
        logger.info("Opening browser and navigating to https://demoqa.com/login  website");
        driver.get("https://demoqa.com/login");
        driver.manage().window().fullscreen();

        logger.info("Closing the pop-up");
        WebElement dataProcessingConsent = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[1]/div[2]/div[2]/button[1]/p"));
        dataProcessingConsent.click();

        logger.info("Locating the username textbox");
        WebElement usernameTextbox = driver.findElement(By.id("userName"));
        usernameTextbox.sendKeys("test123");

        logger.info("Locating the password textbox");
        WebElement passwordTextbox = driver.findElement(By.id("password"));
        passwordTextbox.sendKeys("Test123$");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        logger.info("Locating the login button and attempting to log in");
        WebElement submitButton = driver.findElement(By.id("login"));
        submitButton.click();


        logger.info("Checking if the login was successful by comparing current URL");
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://demoqa.com/profile"));
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("https://demoqa.com/profile", currentUrl);
        logger.info("Login successful");

        logger.info("Locating the Book Store Application button and clicking it");
        WebElement goToStore = driver.findElement(By.id("gotoStore"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", goToStore);

        logger.info("Locating search box and typing in 'Speaking javascript'");
        WebElement searchBox = driver.findElement(By.id("searchBox"));
        searchBox.sendKeys("Speaking javascript");

        logger.info("Locating the book and clicking it");
        WebElement SpeakingJavascript = driver.findElement(By.id("see-book-Speaking JavaScript"));
        SpeakingJavascript.click();

        logger.info("Locating add to collection button");
        WebElement addToCartButton = driver.findElement(By.id("addNewRecordButton"));
        executor.executeScript("arguments[0].click();", addToCartButton);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement profileButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[1]/div/div/div[6]/div/ul/li[3]/span"));
        executor.executeScript("arguments[0].click();", profileButton);

        logger.info("Checking if product has been added to the cart");
        WebElement targetBook = driver.findElement(By.id("see-book-Learning JavaScript Design Patterns"));
        assertEquals("Learning JavaScript Design Patterns", targetBook.getText());
        logger.info("Product added to cart successfully");

        logger.info("Test completed");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
