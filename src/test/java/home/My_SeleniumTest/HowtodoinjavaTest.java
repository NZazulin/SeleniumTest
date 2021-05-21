package home.My_SeleniumTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HowtodoinjavaTest {

    private static WebDriver driver;
    private static WebDriverWait wait;


    @BeforeAll
    static void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        System.setProperty("webdriver.chrome.driver","bin/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver,5,300);
    }

    @Test
    void howtodoinjava() {

        //1. Зайти на страницу howtodoinjava.com
        driver.get("https://howtodoinjava.com/");
        Assertions.assertTrue(driver.getCurrentUrl().startsWith("https://howtodoinjava.com/"), "URL не соответсвует howtodoinjava");
        //2. Найти секцию с уроками Java Coredsfsdf
        String parentCore = "//table//h4[text()='Core Java']/parent::td/ol/li/a";

        List<WebElement> javaCoreElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(parentCore)));

        //3. Выбираем 4 рандомных секции из уроков Java Core
        int n = 4;
        List<WebElement> randomElements = randomList(javaCoreElements, n);
        List<String> elementTitle = new ArrayList<>();
        for (WebElement webElement : randomElements){
            elementTitle.add(webElement.getText());
        }
        //driver.navigate().refresh();
        //4. Для каждой из секций проверить что:
        //      1) Присутствует левый блок тематического меню.
        //      2) Присутствует верхний блок навигационного меню.
        //      3) Присутствует тумблер переключения темной/светлой темы.
        //      4) Тумблер переключить.
        for (int i = 0; i < randomElements.size(); i++){
            try {
                randomElements.get(i).click();
            }
            //Ловим ошибку StaleElementReferenceException. Страница обновилась, нужно заново найти этот элемент
            catch (StaleElementReferenceException e){
                WebElement newSearch = driver.findElement(By.xpath(String.format("%s[text()='%s']", parentCore, elementTitle.get(i))));
                newSearch.click();
            }
            //4.1
            WebElement sideBar = checkElement("//aside[@class = 'sidebar sidebar-secondary widget-area']");
            //4.2
            WebElement topBar = checkElement("//header/div[@class='wrap']");
            //4.3
            WebElement themeSwitcher = checkElement("//div[@class = 'wpnm-button-inner-left']");
            //4.4
            themeSwitcher.click();
            //Возврат на предыдущее окно
            driver.navigate().back();
        }
    }

    private List<WebElement> randomList(List<WebElement> list, int n){
        n++;
        List<WebElement> copy = new ArrayList<>(list);
        Collections.shuffle(copy);
        return copy.subList( 0, n);
    }

    private WebElement checkElement (String XPath){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPath)));
        Assertions.assertNotNull(element);
        return element;
    }

    @AfterAll
    static void tearDown(){
        driver.close();
    }
}
