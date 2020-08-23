package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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



	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testUserSignupLogin() throws InterruptedException {

		driver.get(baseURL + "/login");

		signupPage = new SignupPage(driver);

		String username = "superstar";
		String firstname = "George";
		String lastname = "Bush";
		String password = "immamypassword";

		signupPage.signup(firstname, lastname,username,password);

		assertEquals(baseURL+"/login", driver.getCurrentUrl());

		loginPage = new LoginPage(driver);

		loginPage.login(username, password);

		homePage = new HomePage(driver);

		assertEquals(baseURL + "/home", driver.getCurrentUrl());
		assertEquals("Welcome "+ firstname + lastname, homePage.getName());

		homePage.logout();

		assertEquals(baseURL + "/login?logout", driver.getCurrentUrl());
	}

}
