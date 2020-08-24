package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	public String baseURL;

	private WebDriver driver;

	public SignupPage signupPage;
	public LoginPage loginPage;
	public HomePage homePage;
	public CredentialsTab credentialsTab;
	public NotesTab notesTab;

	public WebDriverWait wait;
	public Boolean mark;
	public WebElement element;

	String firsname = "John";
	String lastname = "Clark";
	String username = "superman";
	String password = "immahere";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
		wait = new WebDriverWait(driver,10);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void testValidUserSignupLogin() throws InterruptedException {

		driver.get(baseURL+"/signup");

		wait.until(ExpectedConditions.titleContains("Sign Up"));
		signupPage = new SignupPage(driver);

		signupPage.signup(firsname,lastname,username,password);

		doLoginFunction();

		assertEquals(baseURL+"/home", driver.getCurrentUrl());
		assertEquals("Welcome "+firsname+" "+lastname, driver.findElement(By.xpath("//div[@id='logoutDiv']//h1")).getText());

		homePage.logout();

		wait.until(ExpectedConditions.titleContains("Login"));

		assertEquals(baseURL+"/login?logout", driver.getCurrentUrl());

	}

	@Test
	@Order(2)
	public void testInvalidUserSignupLogin(){

		String firsname = "John";
		String lastname = "Clark";
		String username = "superma";
		String password = "immahere";

		driver.get(baseURL+"/signup");

		signupPage = new SignupPage(driver);

		signupPage.signup(firsname,lastname,username,password);

		driver.get(baseURL+"/login");
		wait.until(ExpectedConditions.titleContains("Login"));
		assertEquals(baseURL+"/login", driver.getCurrentUrl());

		loginPage = new LoginPage(driver);

		loginPage.login("batman", password);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-msg")));

		assertEquals("Invalid username or password", driver.findElement(By.id("error-msg")).getText());

	}


	@Test
	@Order(3)
	public void testAddNote(){
		doLoginFunction();

		notesTab = new NotesTab(driver);

		notesTab.addNote(driver, "Test Title", "Test Description");

		List<String> detail = notesTab.getDetail();

		assertEquals("Test Title", detail.get(0));
		assertEquals("Test Description", detail.get(1));

		homePage.logout();

		wait.until(ExpectedConditions.titleContains("Login"));

		assertEquals(baseURL+"/login?logout", driver.getCurrentUrl());
	}

	public void doLoginFunction(){
		driver.get(baseURL+"/login");
		wait.until(ExpectedConditions.titleContains("Login"));

		assertEquals(baseURL+"/login", driver.getCurrentUrl());

		loginPage = new LoginPage(driver);

		loginPage.login(username, password);

		driver.get(baseURL+"/home");
		wait.until(ExpectedConditions.titleContains("Home"));

		homePage = new HomePage(driver);
	}

}
