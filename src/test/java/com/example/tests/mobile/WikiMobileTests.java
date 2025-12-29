package com.example.tests.mobile;

import com.example.pages.mobile.WikiMainPage;
import com.example.utils.MobileBaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class WikiMobileTests extends MobileBaseTest {

    @Test(description = "1. Позитивный поиск (Java)")
    public void testSearchWiki() {
        WikiMainPage wikiPage = new WikiMainPage(driver);
        wikiPage.skipOnboarding();

        wikiPage.clickSearch();
        wikiPage.searchFor("Java");

        List<WebElement> results = wikiPage.getSearchResults();
        Assert.assertFalse(results.isEmpty(), "Результаты поиска пусты!");
        Assert.assertTrue(results.get(0).getText().contains("Java"));
    }

    @Test(description = "2. Негативный поиск (Ничего не найдено)")
    public void testNegativeSearch() {
        WikiMainPage wikiPage = new WikiMainPage(driver);
        wikiPage.skipOnboarding();

        wikiPage.clickSearch();
        // Вводим белиберду
        wikiPage.searchFor("kjsdhfksjdhfksjdfh");

        // Проверяем, что список результатов пуст
        boolean isEmpty = wikiPage.isResultsListEmpty();
        Assert.assertTrue(isEmpty, "Найдены результаты там, где их быть не должно!");
    }

    @Test(description = "3. Очистка поиска")
    public void testClearSearch() {
        WikiMainPage wikiPage = new WikiMainPage(driver);
        wikiPage.skipOnboarding();

        wikiPage.clickSearch();
        wikiPage.searchFor("Appium");
        wikiPage.clearSearch();

        String text = wikiPage.getSearchText();
        Assert.assertFalse(text.contains("Appium"), "Поле поиска не очистилось!");
    }
}