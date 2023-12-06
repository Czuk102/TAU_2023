package com.example.laboratorium2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TestScenerio {
    private WebDriver driver;
    private final Logger logger = Logger.getLogger(TestScenerio.class.getName());

    private static Stream<WebDriver> provideWebDrivers() {
        return Stream.of(
                WebDriverManager.firefoxdriver().create());
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
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
        logger.info("Test finished");
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    void shouldOpenBookStoreSiteAndSearchForBookAndAddItToCart(WebDriver driver) {

        this.driver = driver;
        driver.get("https://demoqa.com/books");
        driver.manage().window().maximize();
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        WebElement usernameTextbox = driver.findElement(By.id("userName"));
        usernameTextbox.sendKeys("testuser_1");
        WebElement passwordTextbox = driver.findElement(By.id("password"));
        passwordTextbox.sendKeys("Test@123");
        WebElement submitButton = driver.findElement(By.id("login"));
        submitButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement searchBox = driver.findElement(By.id("searchBox"));
        searchBox.sendKeys("Speaking JavaScript");
        WebElement searchButton = driver.findElement(By.id("basic-addon2"));
        searchButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebElement book = driver.findElement(By.xpath("//*[@id=\"see-book-Speaking JavaScript\"]"));
        book.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        WebElement addToYourCollectionButton = driver.findElement(By.id("addNewRecordButton"));
        addToYourCollectionButton.click();
        Assertions.assertEquals("Book added to your collection.", driver.findElement(By.id("closeSmallModal-ok")).getText());
    }



    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
