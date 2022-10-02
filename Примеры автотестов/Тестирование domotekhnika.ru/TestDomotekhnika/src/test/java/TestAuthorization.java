import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(Lifecycle.PER_CLASS)
public class TestAuthorization {
	private WebDriver driver;
	String baseURL;

	@AfterEach
	public void takeScreenTest() {
		makeScreenshot();
		driver.close();
	}

	@Attachment(value = "Attachment Screenshot", type = "image/png")
	public byte[] makeScreenshot() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@BeforeAll
	public void setUp() {
		baseURL = "https://domotekhnika.ru";
	}

	/*
	 * Авторизация
	 */

	@Test
	@DisplayName("Проверка на ввод в форме авторизации номера телефона корректной длины")
	public void testAuth_89() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("141231111");
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		{
			List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"некорректный ввод\"]"));
			assert (elements.size() == 0);
		}
	}

	@Test
	@DisplayName("Проверка на ввод в форме авторизации номера телефона с меньшим количеством цифр")
	public void testAuth_90() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("14123000");
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		{
			List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"некорректный ввод\"]"));
			assert (elements.size() != 0);
		}
	}

	@Test
	@DisplayName("Проверка на ввод в форме авторизации номера телефона с лишним количеством цифр")
	public void testAuth_91() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("14123111100");
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		String value = driver.findElement(By.cssSelector("[type=\"tel\"]")).getAttribute("value");
		assertEquals(value, "+7 (914) 123-11-11");
	}

	@Test
	@DisplayName("Проверка на ввод в форме авторизации нечислового символа в поле телефона")
	public void testAuth_92() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("й");
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();

		String value = driver.findElement(By.cssSelector("[type=\"tel\"]")).getAttribute("value");
		assertEquals(value, "");
	}

	@Test
	@DisplayName("Проверка на ввод в форме авторизации пароля корректной длины")
	public void testAuth_93() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);

		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys("123456");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		{
			List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"не менее 6 символов\"]"));
			assert (elements.size() == 0);
		}
	}

	@Test
	@DisplayName("Проверка на ввод в форме авторизации пароля с меньшим количеством символов")
	public void testAuth_94() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys("12345");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		{
			List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"не менее 6 символов\"]"));
			assert (elements.size() != 0);
		}
	}

	@Test
	@DisplayName("Проверка авторизации через «ВКонтакте»")
	public void testAuth_95() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector(".default-header-socials .--icon:nth-child(1) svg")).click();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).sendKeys(""); //корректный email
		driver.findElement(By.name("pass")).click();
		driver.findElement(By.name("pass")).sendKeys(""); //корректный пароль
		driver.findElement(By.id("install_allow")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".button_indent")));
		driver.findElement(By.cssSelector(".button_indent")).click();
		{
			List<WebElement> elements = driver.findElements(By.linkText("Вход"));
			assert (elements.size() == 0);
		}
	}

	@Test
	@DisplayName("Проверка авторизации через «Google»")
	public void testAuth_96() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector(".default-header-socials .--icon:nth-child(2) svg")).click();
		String value = driver.findElement(By.cssSelector("#headingText span")).getText();
		assertNotEquals(value, "Ошибка авторизации");
	}

	@Test
	@DisplayName("Проверка авторизации для существующего пользователя в Google Chrome по номеру телефона")
	public void testAuth_98() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys(""); //корректный телефон
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys(""); //корректный пароль
		driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".toast-message")));
		assertEquals(driver.findElement(By.cssSelector(".toast-message")).getText(), "Вы успешно авторизовались");
	}

	@Test
	@DisplayName("Проверка авторизации для существующего пользователя в Mozilla Firefox по номеру телефона")
	public void testAuth_99() throws Throwable {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys(""); //корректный телефон
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys(""); //корректный пароль
		driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".toast-message")));
		assertEquals(driver.findElement(By.cssSelector(".toast-message")).getText(), "Вы успешно авторизовались");
	}

	@Test
	@DisplayName("Проверка авторизации с неверным паролем для существующего пользователя в Google Chrome по номеру телефона")
	public void testAuth_100() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys(""); //корректный телефон
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys("123456");
		driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".toast-message")));
		assertEquals(driver.findElement(By.cssSelector(".toast-message")).getText(), "Неверный пароль");
	}

	@Test
	@DisplayName("Проверка авторизации с неверным паролем для существующего пользователя в Mozilla Firefox по номеру телефона")
	public void testAuth_101() throws Throwable {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys(""); //корректный телефон
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys("123456");
		driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".toast-message")));
		assertEquals(driver.findElement(By.cssSelector(".toast-message")).getText(), "Неверный пароль");
	}

	@Test
	@DisplayName("Проверка авторизации для несуществующего пользователя в Google Chrome по номеру телефона")
	public void testAuth_102() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("146811033");
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys("123456");
		driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".toast-message")));
		assertEquals(driver.findElement(By.cssSelector(".toast-message")).getText(), "Пользователь не найден");
	}

	@Test
	@DisplayName("Проверка авторизации для несуществующего пользователя в Mozilla Firefox по номеру телефона")
	public void testAuth_103() throws Throwable {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Вход")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("146811033");
		driver.findElement(By.cssSelector("[type=\"password\"]")).click();
		driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys("123456");
		driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".toast-message")));
		assertEquals(driver.findElement(By.cssSelector(".toast-message")).getText(), "Пользователь не найден");
	}
}