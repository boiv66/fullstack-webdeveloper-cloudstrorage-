package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.close();
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
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
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password) {
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
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
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

	@Test
	public void testCreateDisplayNote(){
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();
//


		//wait for note modal to appear before verifying
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
		Assertions.assertTrue(driver.findElement(By.id("nav-notes")).isDisplayed());

		// click on add new note button
		WebElement newNoteBtn = driver.findElement(By.id("newNoteBtn"));
		newNoteBtn.click();


		// Fill out the form
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.sendKeys("Note Title Test");
//
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.sendKeys("Testing description");

		// Attempt ot submit the note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitNoteBtn")));
		WebElement submitNote = driver.findElement(By.id("submitNoteBtn"));
		submitNote.click();

		//go back to home page, and then hit note tab;
		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		//Check if added note display note title and note description
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("noteTitleTable")).getText().contains("Note Title Test"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("noteDescriptionTable")).getText().contains("Testing description"));
	}

	@Test
	public void testEditNote(){
		testCreateDisplayNote();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// open edit modal
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNoteBtn")));
		WebElement editNoteBtn = driver.findElement(By.id("editNoteBtn"));
		editNoteBtn.click();

		// change the title and description of the note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.clear();
		inputTitle.sendKeys("Test Edit Note");
//
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.clear();
		inputDescription.sendKeys("Testing edit description");

		// Save the edited change
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitNoteBtn")));
		WebElement submitNote = driver.findElement(By.id("submitNoteBtn"));
		submitNote.click();

		//go back to home page, and then hit note tab;
		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		//Check if the edited note have successfully update in the table display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("noteTitleTable")).getText().contains("Test Edit Note"));
//
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("noteDescriptionTable")).getText().contains("Testing edit description"));
	}

	@Test
		public void testDeleteNote (){
		testCreateDisplayNote();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNoteBtn")));
		WebElement deleteNoteBtn  = driver.findElement(By.id("deleteNoteBtn"));
		deleteNoteBtn.click();

		//go back to home page, and then hit note tab;
		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		//Check if the edited note have successfully delete or not appear in the table display
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.className("noteBodyTable"));
		Assertions.assertEquals(0, notesList.size());
	}

	@Test
	public void testCreateCredential(){
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialTab.click();


		//wait for note modal to appear before verifying
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newCredentialBtn")));
		Assertions.assertTrue(driver.findElement(By.id("newCredentialBtn")).isDisplayed());
//
		// click on add new note button
		WebElement newCredentialBtn = driver.findElement(By.id("newCredentialBtn"));
		newCredentialBtn.click();

//
		// Fill out the form
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputUrl = driver.findElement(By.id("credential-url"));
		inputUrl.click();
		inputUrl.sendKeys("Credential Url Test");
////
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputUsername = driver.findElement(By.id("credential-username"));
		inputUsername.click();
		inputUsername.sendKeys("Credential Username Test");
//
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputPassword = driver.findElement(By.id("credential-password"));
		inputPassword.click();
		inputPassword.sendKeys("CredentialPasswordTest");

		// Attempt ot submit the credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitCredentialBtn")));
		WebElement submitCredential = driver.findElement(By.id("submitCredentialBtn"));
		submitCredential.click();
//
		//go back to home page, and then hit note tab;
		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();
//

		//Check if added note display note title and note description
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("credentialUrlTable")).getText().contains("Credential Url Test"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("credentialUsernameTable")).getText().contains("Credential Username Test"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertFalse(driver.findElement(By.id("credentialPasswordTable")).getText().contains("CredentialPasswordTest"));


	}

	@Test
	public void editCredential(){
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		// click on edit button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCredentialBtn")));
		WebElement editCredentialsButton= driver.findElement(By.id("editCredentialBtn"));
		editCredentialsButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputURL = driver.findElement(By.id("credential-url"));
		inputURL.click();
		inputURL.clear();
		inputURL.sendKeys("https://facebook.com");

		// get the plain password
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialPasswordTable")));
		String inputPassword = driver.findElement(By.id("credentialPasswordTable")).getAttribute("value");

		// Attempt ot submit the changes in the credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitCredentialBtn")));
		WebElement submitCredential = driver.findElement(By.id("submitCredentialBtn"));
		submitCredential.click();

		//go back to home page, and then hit note tab;
		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("credentialUrlTable")).getText().contains("https://facebook.com"));

	}

	@Test
	public void testDeleteCredential(){
		testCreateCredential();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentialBtn")));
		WebElement deleteCredentialButton= driver.findElement(By.id("deleteCredentialBtn"));
		deleteCredentialButton.click();

		//go back to home page, and then hit credential tab;
		driver.get("http://localhost:" + this.port + "/home");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		//Check if the edited credential have successfully delete or not appear in the table display
		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credentialList = credentialTable.findElements(By.className("credentialBodyTable"));
		Assertions.assertEquals(0, credentialList.size());



	}

	@Test
	public void testSignUpLoginLogout(){
//		driver.get("http://localhost:" + this.port + "/signup");
//		doMockSignUp("Adam", "Chris", "adam", "test");


		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		WebElement logoutBtn = driver.findElement(By.id("logOutBtn"));
		logoutBtn.click();
		// check if it is logout and return to login page
		Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertFalse(driver.getTitle().equals("Home"));
	}

}
