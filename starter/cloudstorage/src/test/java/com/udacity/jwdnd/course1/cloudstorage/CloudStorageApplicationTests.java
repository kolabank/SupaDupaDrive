package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;
	private final String credentialURL = "kolabank.com";
	private final String credentialUsername = "Kola";
	private final String credentialPassword = "kolade123";

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
	public void testUnauthorizedAccess(){//Test that verifies that an authorized user can only access the login and signup oages

		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());

		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());


	}

	@Test
	public void testSignupAndLogin() { //Test that signs up a user, logs in , verifies that home page is accessible, logs out and verifies that home page is no longer accessible
		String firsName = "Kolade";
		String lastName = "Bamky";
		String username = "kola";
		String password = "temppassword";

//		Testing the signup
		SignupPage signupPage = new SignupPage(driver);
		driver.get(baseURL + "/signup");
		signupPage.signUp(firsName, lastName, username, password);

//		Testing the login
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginTest(username, password);

//		Test if home page is accessible
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

	}
	@Test
		public void testLogoutAndInaccessibility(){
		testSignupAndLogin();
//		Log out
		HomePage homePage = new HomePage(driver);
		homePage.testLogout();

//		Check if home can be assessed
		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());

	}


//	Test for Note creation, viewing, editing and deletion
	//Test for note creation
	@Test
	public void testNoteCreation(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		testSignupAndLogin(); //sign up and log in first
		String noteTitle = "First note";
		String noteDescription = "This is the first note";

	//	Test for note creation

		NotePage notePage = new NotePage(driver);

		notePage.clickNavNoteTab();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewNote")));
		notePage.addNote();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		notePage.newNoteTitle(noteTitle);
		notePage.newNoteDescription(noteDescription);
		notePage.noteSubmit();

		//Test for note viewing

		notePage.clickNavNoteTab();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitle")));
		Assertions.assertEquals(noteTitle, notePage.viewTitle());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescription")));
		Assertions.assertEquals(noteDescription, notePage.viewDescription());

	}


	@Test
	public void testEditNote(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		testNoteCreation();
		String editedTitle = "Edited First Note";
		String editedNote = "This is the edited first note";

		NotePage notePage = new NotePage(driver);
		notePage.editButtonClick();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		notePage.editNoteTitle(editedTitle);
		notePage.noteSubmit();

		notePage.clickNavNoteTab();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitle")));
		Assertions.assertEquals(editedTitle,  notePage.viewTitle());

	}


	@Test
	public void testDeleteNote(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		testNoteCreation();
		boolean elementPresent = false;
		NotePage notePage = new NotePage(driver);
		notePage.deleteNote();
		notePage.clickNavNoteTab();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewNote")));

		List<WebElement> elements = driver.findElements(By.id("noteTitle"));

		if(elements.size()>0){elementPresent = true;}

		Assertions.assertEquals(false, elementPresent);


	}



//	Test for Credential creation, viewing, editing and deletion
	//Test for Credential creation
	@Test
	public void testCredentialCreation(){

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		testSignupAndLogin(); //sign up and log in first

		//	Test for note creation

		CredentialPage credentialPage = new CredentialPage(driver);

		credentialPage.clickCredentialsTab();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewCredential")));
		credentialPage.addNewCredentials();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-URL")));
		credentialPage.fillURL(credentialURL);
		credentialPage.fillUsername(credentialUsername);
		credentialPage.fillPassword(credentialPassword);

		credentialPage.saveChanges();

		//Test for credential availability

		credentialPage.clickCredentialsTab();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewCredential")));
		Assertions.assertEquals(credentialURL, credentialPage.viewURL());
		Assertions.assertEquals(credentialUsername, credentialPage.viewUsername());
		//Show that credentialPassword exists and is shown
		Assertions.assertNotNull(credentialPage.viewPassword());
		//Test that credentialPassword shown is not the same as what was inputted (encrypted)
		Assertions.assertNotEquals(credentialPassword, credentialPage.viewPassword());

	}

	//Test for credential editing
	@Test
	public void testCredentialEditing(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		CredentialPage credentialPage = new CredentialPage(driver);
		final String editedURL = "editedKolabank.com";
		final String editedUsername ="editedKola";
		final String editedPassword = "editedKola123";

		//Create a credential
		testCredentialCreation();

		credentialPage.clickCredentialEdit();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-URL")));
		credentialPage.fillURL(editedURL);
		credentialPage.fillUsername(editedUsername);
		credentialPage.fillPassword(editedPassword);

		credentialPage.saveChanges();

		credentialPage.clickCredentialsTab();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewCredential")));
		Assertions.assertEquals(editedURL, credentialPage.viewURL());
		Assertions.assertEquals(editedUsername, credentialPage.viewUsername());
		Assertions.assertNotEquals(editedPassword, credentialPage.viewPassword());

	}


	//Test for Credential creation
	@Test
	public void testCredentialDeletion(){
		testCredentialCreation();
		boolean elementPresent = true;

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		CredentialPage credentialPage = new CredentialPage(driver);

		credentialPage.clickCredentialDelete();
		credentialPage.clickCredentialsTab();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewCredential")));

		List<WebElement> elements = driver.findElements(By.id("credentialURL"));

		if(elements.size()<1){elementPresent = false;}

		Assertions.assertEquals(false, elementPresent);

	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
//		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");


		// Check if we have been redirected to the log in page.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
