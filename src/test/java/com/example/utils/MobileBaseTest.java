package com.example.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class MobileBaseTest {
    protected AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        // Проверь путь к файлу, если он отличается
        File appDir = new File("src/resources/wiki.apk");

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 7");
        options.setApp(appDir.getAbsolutePath());
        options.setAutomationName("UiAutomator2");
        options.setPlatformName("Android");

        // ВАЖНО: Разрешаем любой стартовый экран (Onboarding или Main)
        options.setAppWaitActivity("*");

        // Инициализация драйвера
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}