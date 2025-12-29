package com.example.pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WikiMainPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    private By searchContainer = AppiumBy.id("org.wikipedia:id/search_container");
    private By searchInput = AppiumBy.id("org.wikipedia:id/search_src_text");
    private By searchCloseBtn = AppiumBy.id("org.wikipedia:id/search_close_btn");
    private By resultTitle = AppiumBy.id("org.wikipedia:id/page_list_item_title");

    // Локатор сообщения "Ничего не найдено" (может отличаться, поэтому берем по ID контейнера или текста)
    private By emptyMessage = AppiumBy.id("org.wikipedia:id/results_text");

    public WikiMainPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void skipOnboarding() {
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        try {
            driver.findElement(AppiumBy.id("org.wikipedia:id/fragment_onboarding_skip_button")).click();
        } catch (Exception e) {
            try {
                driver.findElement(AppiumBy.xpath("//*[contains(@text, 'Skip')]")).click();
            } catch (Exception ex) { }
        }
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchContainer)).click();
    }

    public void searchFor(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).sendKeys(text);
    }

    public void clearSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchCloseBtn)).click();
    }

    public String getSearchText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).getText();
    }

    public List<WebElement> getSearchResults() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(resultTitle));
    }

    // Метод: Проверяем, что результатов НЕТ
    public boolean isResultsListEmpty() {
        try {
            // Ждем 2 секунды, если результатов нет - значит пусто
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.visibilityOfElementLocated(resultTitle));
            return false; // Нашли результаты
        } catch (Exception e) {
            return true; // Результатов не нашли (TimeOut)
        }
    }
}