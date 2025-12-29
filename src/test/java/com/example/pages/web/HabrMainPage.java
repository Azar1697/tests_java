package com.example.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList; // <--- Вот этот импорт потерялся
import java.util.List;

import java.time.Duration;


public class HabrMainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Локаторы ---
    private By searchButton = By.cssSelector("a[href*='/search']");
    private By searchInput = By.cssSelector("input[name='q']");
    private By articleTitles = By.cssSelector("h2.tm-title span");
    private By logo = By.cssSelector("a.tm-header__logo");
    private By pageHeader = By.cssSelector("h1");
    private By loginButton = By.xpath("//a[contains(text(), 'Войти')]");

    // ИЗМЕНЕНИЕ: Ищем ссылку "Новости" (она точно есть на экране)
    // Используем contains, так как там может быть счетчик (+25)
    private By newsLink = By.xpath("//a[contains(text(), 'Новости')]");

    // --- Конструктор ---
    public HabrMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Методы ---
    public void open() {
        driver.get("https://habr.com/ru/articles/");
    }

    public void searchFor(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.sendKeys(text);
        input.sendKeys(Keys.ENTER);
    }

    public List<String> getArticleTitles() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(articleTitles));
        List<WebElement> titleElements = driver.findElements(articleTitles);

        List<String> texts = new ArrayList<>();
        // Берем только первые 5 (или меньше, если нашлось мало)
        int count = Math.min(titleElements.size(), 5);

        for (int i = 0; i < count; i++) {
            texts.add(titleElements.get(i).getText());
        }
        return texts;
    }

    // ИЗМЕНЕНИЕ: Метод клика по Новостям
    public void clickNewsSection() {
        System.out.println("Кликаю по разделу 'Новости'...");
        wait.until(ExpectedConditions.elementToBeClickable(newsLink)).click();
    }

    public String getPageHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader)).getText();
    }

    public void clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(logo)).click();
    }

    public boolean isLoginButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
    }
}