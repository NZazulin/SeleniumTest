package home.My_SeleniumTest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YandexTest {

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
    @DisplayName("Проверка авторизации без ввода пароля")
    void yandexAlternative(){

        //1. Зайти на страницу yandex.ru
        driver.get("https://yandex.ru");
        Assertions.assertTrue(driver.getCurrentUrl().startsWith("https://yandex.ru"), "URL не соответсвует Yandex");

        //2. В правом верхнем виджете кликнуть по кнопке с названием "Войти"
        WebElement loginButton = driver.findElement(By.xpath("//a[@data-statlog='notifications.mail.logout.enter']"));
        loginButton.click();

        //3. Не вводя логин, нажать на кнопку "Войти".
        WebElement loginAuthorisationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@data-t='button:action']")));
        loginAuthorisationButton.click();

        //4. Проверить, что под полем ввода отобразилось предупреждение "Логин не указан".
        WebElement hintText = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='Textinput-Hint Textinput-Hint_state_error']")));
        Assertions.assertEquals("Логин не указан",hintText.getText());
    }

    @AfterAll
    static void tearDown(){
        driver.close();
    }
}
