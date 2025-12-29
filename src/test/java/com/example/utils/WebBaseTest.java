package com.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.time.Duration;

public class WebBaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Скачиваем драйвер для Chrome
        WebDriverManager.chromedriver().setup();

        // Настройки для браузера
        ChromeOptions options = new ChromeOptions();

        // ВАЖНО: Стратегия EAGER.
        // Selenium не будет ждать полной загрузки всех картинок и рекламы.
        // Он начнет тест сразу, как только загрузится HTML-каркас.
        // Это решит проблему с TimeoutException на тяжелых сайтах типа Хабра.
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        // Эти опции нужны для стабильной работы новых версий Chrome (143+)
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized"); // Запуск во весь экран

        // Инициализация драйвера с настройками
        driver = new ChromeDriver(options);

        // Общие тайм-ауты
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }

    @AfterMethod
    public void tearDown() {
        // Закрываем браузер после каждого теста
        if (driver != null) {
            driver.quit();
        }
    }
}