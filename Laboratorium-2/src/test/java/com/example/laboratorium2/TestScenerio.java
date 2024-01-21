package com.example.laboratorium2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
