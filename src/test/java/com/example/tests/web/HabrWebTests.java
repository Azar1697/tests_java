package com.example.tests.web;

import com.example.pages.web.HabrMainPage;
import com.example.utils.WebBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

public class HabrWebTests extends WebBaseTest {

    @Test(description = "Проверка поиска на сайте")
    public void testSearchFunctionality() {
        String query = "DevOps";
        HabrMainPage mainPage = new HabrMainPage(driver);
        mainPage.open();

        mainPage.searchFor(query);

        // Получаем список заголовков
        List<String> titles = mainPage.getArticleTitles();

        boolean isFound = false;
        for (String title : titles) {
            System.out.println("Найден заголовок: " + title);
            if (title.toLowerCase().contains(query.toLowerCase())) {
                isFound = true;
                break; // Нашли совпадение, выходим
            }
        }

        Assert.assertTrue(isFound, "Ни в одном из первых заголовков не найдено слово: " + query);
    }

    @Test(description = "Проверка навигации в раздел Новости")
    public void testNavigationToNews() {
        HabrMainPage mainPage = new HabrMainPage(driver);
        mainPage.open();

        // Переходим в Новости
        mainPage.clickNewsSection();

        // Проверяем, что URL изменился (содержит /news/)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/news/"),
                "URL не содержит /news/, текущий: " + currentUrl);
    }

    @Test(description = "Клик по логотипу возвращает на главную")
    public void testLogoClick() {
        HabrMainPage mainPage = new HabrMainPage(driver);
        mainPage.open();

        // Сначала переходим в Новости
        mainPage.clickNewsSection();

        // Кликаем по лого
        mainPage.clickLogo();

        // Проверяем, что вернулись (URL стал базовым или лентой)
        String currentUrl = driver.getCurrentUrl();
        boolean isMainPage = currentUrl.equals("https://habr.com/ru/articles/") ||
                currentUrl.equals("https://habr.com/ru/") ||
                currentUrl.contains("/feed/"); // <-- Добавили /feed/

        Assert.assertTrue(isMainPage, "Не вернулись на главную страницу, текущий URL: " + currentUrl);
    }

    @Test(description = "Проверка отображения кнопки 'Войти'")
    public void testLoginButtonDisplay() {
        HabrMainPage mainPage = new HabrMainPage(driver);
        mainPage.open();

        // Проверяем, что кнопка входа видна
        Assert.assertTrue(mainPage.isLoginButtonDisplayed(), "Кнопка 'Войти' не отображается!");
    }
}