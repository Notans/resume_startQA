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
import io.qameta.allure.Attachment;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(Lifecycle.PER_CLASS)
public class TestRegistration {
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
	 * Регистрация
	 */

	@Test
	@DisplayName("Проверка на ввод корректного номера телефона в форме регистрации")
	public void testReg_45() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"tel\"]")));
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("141231111");
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"некорректный ввод\"]"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации номера телефона с меньшим количеством цифр")
	public void testReg_46() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"tel\"]")));
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("14123000");
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"некорректный ввод\"]"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации номера телефона с лишним количеством цифр")
	public void testReg_47() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"tel\"]")));
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("14123111100");
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		String value = driver.findElement(By.cssSelector("[type=\"tel\"]")).getAttribute("value");
		assertEquals(value, "+7 (914) 123-11-11");
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации нечислового символа в поле телефона")
	public void testReg_48() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"tel\"]")));
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("й");
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		String value = driver.findElement(By.cssSelector("[type=\"tel\"]")).getAttribute("value");
		assertEquals(value, "");
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации кода с меньшим количеством цифр")
	public void testReg_50() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"tel\"]")));
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("141231111");
		WebElement element = driver.findElement(By.cssSelector(".phone-confirm .input"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		driver.findElement(By.cssSelector(".phone-confirm .input")).click();
		driver.findElement(By.cssSelector(".phone-confirm .input")).sendKeys("123");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".invalid-code"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации кода с большим количеством цифр")
	public void testReg_51() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"tel\"]")));
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("141231111");
		WebElement element = driver.findElement(By.cssSelector(".phone-confirm .input"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		driver.findElement(By.cssSelector(".phone-confirm .input")).click();
		driver.findElement(By.cssSelector(".phone-confirm .input")).sendKeys("12345");
		List<WebElement> elements = driver.findElements(By.cssSelector(".invalid-code"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации нечислового символа в поле кода")
	public void testReg_52() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"tel\"]")));
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("141231111");
		WebElement element = driver.findElement(By.cssSelector(".phone-confirm .input"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		driver.findElement(By.cssSelector(".phone-confirm .input")).click();
		driver.findElement(By.cssSelector(".phone-confirm .input")).sendKeys("й");
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		String value = driver.findElement(By.cssSelector(".phone-confirm .input")).getAttribute("value");
		assertEquals(value, "");
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного ФИО")
	public void testReg_53() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).sendKeys("Иванов Петр Семенович");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".-abs"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного ФИО с двойной фамилией")
	public void testReg_54() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]"))
				.sendKeys("Сергеев-Андрейко Максим Александрович");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".-abs"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного ФИО с двойным именем")
	public void testReg_55() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]"))
				.sendKeys("Борисова Алла-Виктория Артемовна");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".-abs"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации латиницы в поле ФИО")
	public void testReg_56() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).sendKeys("John Doe Петрович");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".-abs"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации ФИО лишних слов")
	public void testReg_57() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]"))
				.sendKeys("Андреев Петр Семенович Максимович");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		String value = driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).getAttribute("value");
		assertEquals(value, "Андреев Петр Семенович");
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации ФИО недостающих данных")
	public void testReg_58() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).sendKeys("Сидоров");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".-abs"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации ФИО символов, отличных от букв")
	public void testReg_59() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).sendKeys("1");
		String value = driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).getAttribute("value");
		assertEquals(value, "");
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного E-mail")
	public void testReg_60() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")).sendKeys("email@email.ru");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".-abs"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации E-mail с кириллицей")
	public void testReg_61() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")).sendKeys("почта@почта.ru");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".-abs"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации E-mail символов, отличных от формата E-mail")
	public void testReg_62() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")));
		driver.findElement(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")).sendKeys("1");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector(".-abs"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного дня рождения")
	public void testReg_63() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("15");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"неправильная дата\"]"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного дня рождения (минимальное значение)")
	public void testReg_64() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("1");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"неправильная дата\"]"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного дня рождения (максимальное значение)")
	public void testReg_65() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("31");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"неправильная дата\"]"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации некорректного дня рождения (выход за нижнюю границу)")
	public void testReg_66() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("0");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"неправильная дата\"]"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации некорректного дня рождения (выход за верхнюю границу)")
	public void testReg_67() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("32");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"неправильная дата\"]"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации нечислового символа в поле дня рождения")
	public void testReg_68() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("й");
		driver.findElement(By.xpath("//input[contains(@type,'tel')]")).click();
		String value = driver.findElement(By.cssSelector(".-cut-right")).getAttribute("value");
		assertEquals(value, "");
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного месяца рождения")
	public void testReg_69() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".select")));
		Select dropMonth = new Select(driver.findElement(By.cssSelector(".select")));
		dropMonth.selectByIndex(1);
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"неправильная дата\"]"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации некорректного месяца рождения")
	public void testReg_70() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".select")));
		WebElement elementScroll = driver.findElement(By.cssSelector(".select"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
		driver.findElement(By.cssSelector(".select")).click();
		driver.findElement(By.cssSelector(".select")).sendKeys("1");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		String value = driver.findElement(By.cssSelector(".select")).getAttribute("value");
		assertEquals(value, "");
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного года рождения")
	public void testReg_71() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-left")));
		WebElement elementScroll = driver.findElement(By.cssSelector(".-cut-left"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
		driver.findElement(By.cssSelector(".-cut-left")).click();
		driver.findElement(By.cssSelector(".-cut-left")).sendKeys("1999");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"неправильная дата\"]"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного года рождения (нижняя граница диапазона)")
	public void testReg_72() throws Throwable {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH) + 1;
		String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, new Locale("ru"));

		int monthInd;
		switch (month) {
		case "января":
			monthInd = 1;
			break;
		case "февраля":
			monthInd = 2;
			break;
		case "марта":
			monthInd = 3;
			break;
		case "апреля":
			monthInd = 4;
			break;
		case "мая":
			monthInd = 5;
			break;
		case "июня":
			monthInd = 6;
			break;
		case "июля":
			monthInd = 7;
			break;
		case "августа":
			monthInd = 8;
			break;
		case "сентября":
			monthInd = 9;
			break;
		case "октября":
			monthInd = 10;
			break;
		case "ноября":
			monthInd = 11;
			break;
		case "декабря":
			monthInd = 12;
			break;
		default:
			monthInd = 0;
			break;
		}

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys(Integer.toString(day));
		Select dropMonth = new Select(driver.findElement(By.cssSelector(".select")));
		dropMonth.selectByIndex(monthInd);
		WebElement elementScroll = driver.findElement(By.cssSelector(".-cut-left"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
		driver.findElement(By.cssSelector(".-cut-left")).click();
		driver.findElement(By.cssSelector(".-cut-left")).sendKeys("1921");
		driver.findElement(By.cssSelector(".Button.mb-3")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"не старше 100 лет\"]"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации корректного года рождения (верхняя граница диапазона)")
	public void testReg_73() throws Throwable {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, new Locale("ru"));

		int monthInd;
		switch (month) {
		case "января":
			monthInd = 1;
			break;
		case "февраля":
			monthInd = 2;
			break;
		case "марта":
			monthInd = 3;
			break;
		case "апреля":
			monthInd = 4;
			break;
		case "мая":
			monthInd = 5;
			break;
		case "июня":
			monthInd = 6;
			break;
		case "июля":
			monthInd = 7;
			break;
		case "августа":
			monthInd = 8;
			break;
		case "сентября":
			monthInd = 9;
			break;
		case "октября":
			monthInd = 10;
			break;
		case "ноября":
			monthInd = 11;
			break;
		case "декабря":
			monthInd = 12;
			break;
		default:
			monthInd = 0;
			break;
		}

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys(Integer.toString(day));
		Select dropMonth = new Select(driver.findElement(By.cssSelector(".select")));
		dropMonth.selectByIndex(monthInd);
		WebElement elementScroll = driver.findElement(By.cssSelector(".-cut-left"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
		driver.findElement(By.cssSelector(".-cut-left")).click();
		driver.findElement(By.cssSelector(".-cut-left")).sendKeys("2004");
		driver.findElement(By.cssSelector(".Button.mb-3")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"не моложе 18 лет\"]"));
		assert (elements.size() == 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации некорректного года рождения (выход за нижнюю границу)")
	public void testReg_74() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("1");
		Select dropMonth = new Select(driver.findElement(By.cssSelector(".select")));
		dropMonth.selectByIndex(1);
		WebElement elementScroll = driver.findElement(By.cssSelector(".-cut-left"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
		driver.findElement(By.cssSelector(".-cut-left")).click();
		driver.findElement(By.cssSelector(".-cut-left")).sendKeys("1921");
		driver.findElement(By.cssSelector(".Button.mb-3")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"не старше 100 лет\"]"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации некорректного года рождения (выход за верхнюю границу)")
	public void testReg_75() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-right")));
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("1");
		Select dropMonth = new Select(driver.findElement(By.cssSelector(".select")));
		dropMonth.selectByIndex(1);
		WebElement elementScroll = driver.findElement(By.cssSelector(".-cut-left"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
		driver.findElement(By.cssSelector(".-cut-left")).click();
		driver.findElement(By.cssSelector(".-cut-left")).sendKeys("2005");
		driver.findElement(By.cssSelector(".Button.mb-3")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//small[text()=\"не старше 100 лет\"]"));
		assert (elements.size() != 0);
	}

	@Test
	@DisplayName("Проверка на ввод в форме регистрации нечислового символа в поле года")
	public void testReg_76() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".-cut-left")));
		WebElement elementScroll = driver.findElement(By.cssSelector(".-cut-left"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
		driver.findElement(By.cssSelector(".-cut-left")).click();
		driver.findElement(By.cssSelector(".-cut-left")).sendKeys("й");
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		String value = driver.findElement(By.cssSelector(".-cut-left")).getAttribute("value");
		assertEquals(value, "");
	}

	@Test
	@DisplayName("Проверка возможности зарегистрироваться с неверным кодом")
	public void testReg_77() throws Throwable { //
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		driver.findElement(By.linkText("Регистрация")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=\"tel\"]")));
		driver.findElement(By.cssSelector("[type=\"tel\"]")).click();
		driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("222222222");
		WebElement element = driver.findElement(By.cssSelector(".phone-confirm .input"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		driver.findElement(By.cssSelector(".phone-confirm .input")).click();
		driver.findElement(By.cssSelector(".phone-confirm .input")).sendKeys("1234");
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"fio\"] [type=\"text\"]")).sendKeys("Сергеев Петр");
		driver.findElement(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]")).click();
		driver.findElement(By.cssSelector("[data-qa=\"label-undefined\"] [type=\"text\"]"))
				.sendKeys("pochta@wrongpochta.com");
		driver.findElement(By.cssSelector(".-cut-right")).click();
		driver.findElement(By.cssSelector(".-cut-right")).sendKeys("15");
		WebElement elementScroll = driver.findElement(By.cssSelector(".select"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
		driver.findElement(By.cssSelector(".select")).click();
		Select dropdown = new Select(driver.findElement(By.cssSelector(".select")));
		dropdown.selectByIndex(1);
		driver.findElement(By.cssSelector(".-cut-left")).click();
		driver.findElement(By.cssSelector(".-cut-left")).sendKeys("1999");
		driver.findElement(By.cssSelector(".Button.mb-3")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".toast-message")));
		assertEquals(driver.findElement(By.cssSelector(".toast-message")).getText(), "Код подтверждения не найден");
	}
}