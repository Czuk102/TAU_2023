package com.example.laboratorium2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.stream.Stream;

public class TestScenerio {
    private WebDriver driver;

    private static Stream<WebDriver> provideWebDrivers() {
        return Stream.of(
                WebDriverManager.firefoxdriver().create(),
                WebDriverManager.chromedriver().create());
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    void shouldSuccessfullyLoginAndReachInventorySite(WebDriver driver) {

        this.driver = driver;
        driver.get("https://www.saucedemo.com/");
        WebElement usernameTextbox = driver.findElement(By.id("user-name"));
        usernameTextbox.sendKeys("standard_user");
        WebElement passwordTextbox = driver.findElement(By.id("password"));
        passwordTextbox.sendKeys("secret_sauce");
        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());    
        
        driver.quit();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
