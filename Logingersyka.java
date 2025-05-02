package testelogindio.loginusuarioexistente;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Logingersyka {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Teste de login com usuário existente")
    public void testLogarComUsuarioExistente() {
        try {
            // 1. Acessar o site
            driver.get("https://www.dio.me/");

            // 2. Esperar a página carregar completamente
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

            // 3. Localizar o botão Entrar com estratégia mais flexível
            WebElement entrarButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class, 'sc-Nxspf') and contains(text(), 'Entrar')]")));

            // 4. Rolar até o elemento e clicar
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", entrarButton);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", entrarButton);

            // Restante do fluxo de login...
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("USERNAME")));
            emailField.sendKeys("SEUEMAIL@gmail.com");

            WebElement passwordField = driver.findElement(By.id("password"));
            passwordField.sendKeys("SUASENHA");

            WebElement loginButton = driver.findElement(By.id("kc-login"));
            loginButton.click();

            wait.until(ExpectedConditions.urlContains("home"));

        } catch (Exception e) {
            // Capturar screenshot em caso de falha (opcional)
            // File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            throw e;
        }
    }
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}