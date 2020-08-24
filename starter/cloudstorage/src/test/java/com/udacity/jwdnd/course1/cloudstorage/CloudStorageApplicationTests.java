package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
		wait = new WebDriverWait(driver,60);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testValidUserSignupLogin(){

		String firsname = "John";
		String lastname = "Clark";
		String username = "superman";
		String password = "immahere";

		driver.get(baseURL+"/signup");

		signupPage = new SignupPage(driver);

		signupPage.signup(firsname,lastname,username,password);

		wait.until(ExpectedConditions.titleContains("Login"));
		assertEquals(baseURL+"/login", driver.getCurrentUrl());

		loginPage = new LoginPage(driver);

		loginPage.login(username, password);

		wait.until(ExpectedConditions.titleContains("Home"));

		homePage = new HomePage(driver);
		assertEquals(baseURL+"/home#", driver.getCurrentUrl());
		assertEquals("Welcome "+firsname+" "+lastname, driver.findElement(By.xpath("//div[@id='logoutDiv']//h1")).getText());

		homePage.logout();

		wait.until(ExpectedConditions.titleContains("Login"));

		assertEquals(baseURL+"/login?logout", driver.getCurrentUrl());

	}



}
