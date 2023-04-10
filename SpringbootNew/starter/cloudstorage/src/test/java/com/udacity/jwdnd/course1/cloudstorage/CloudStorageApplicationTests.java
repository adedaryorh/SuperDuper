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

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    private final static String NOTE_TITLE = "Note title";
    private final static String NOTE_DESCRIPTION = "Note description";
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
            driver.quit();
        }
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
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
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
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() throws InterruptedException {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Thread.sleep(2000);
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
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
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
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
    public void getUnauthorizedHomePage() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }
    @Test
    public void testCreateEditAndDeleteNote() {
        createNote(driver, "createNote");
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
        List<WebElement> notesList1 = notesTable.findElements(By.tagName("td"));
        Optional<WebElement> noteCreatedTitle = notesList.stream().filter(note -> note.getAttribute("innerHTML").equals(NOTE_TITLE)).findAny();
        Optional<WebElement> noteCreatedDescription = notesList1.stream().filter(note -> note.getAttribute("innerHTML").equals(NOTE_DESCRIPTION)).findAny();
        Assertions.assertTrue(noteCreatedTitle.isPresent());
        Assertions.assertTrue(noteCreatedDescription.isPresent());

        editNote(driver);
        WebElement editedNotesTable = driver.findElement(By.id("userTable"));
        List<WebElement> editedNotesList = editedNotesTable.findElements(By.tagName("th"));
        List<WebElement> editedNotesList1 = editedNotesTable.findElements(By.tagName("td"));
        Optional<WebElement> noteEditedTitle = editedNotesList.stream().filter(note -> note.getAttribute("innerHTML").equals("new title")).findAny();
        Optional<WebElement> noteEditedDescription = editedNotesList1.stream().filter(note -> note.getAttribute("innerHTML").equals("new descriptions")).findAny();
        Assertions.assertTrue(noteEditedTitle.isPresent());
        Assertions.assertTrue(noteEditedDescription.isPresent());

        deleteNote(driver);
        WebElement deletedNotesTable = driver.findElement(By.id("userTable"));
        List<WebElement> deletedNotesList = deletedNotesTable.findElements(By.tagName("th"));
        List<WebElement> deletedNotesList1 = deletedNotesTable.findElements(By.tagName("td"));
        Optional<WebElement> noteDeletedTitle = deletedNotesList.stream().filter(note -> note.getAttribute("innerHTML").equals(NOTE_TITLE)).findAny();
        Optional<WebElement> noteDeletedDescription = deletedNotesList1.stream().filter(note -> note.getAttribute("innerHTML").equals(NOTE_DESCRIPTION)).findAny();
        Assertions.assertTrue(noteDeletedTitle.isEmpty());
        Assertions.assertTrue(noteDeletedDescription.isEmpty());
    }


    @Test
    public void testCreateEditAndDeleteCredential() {
        // perform mock sign up
        doMockSignUp("ade", "dayo", "adedaryorh", "Olatejumi");

        // log in
        doLogIn("adedaryorh", "Olatejumi");

        // create credential
        createCredential(driver, "create");

        // verify that credential was created
        WebElement notesTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
        List<WebElement> notesList1 = notesTable.findElements(By.tagName("td"));
        Optional<WebElement> createdUrl = notesList.stream().filter(note -> note.getAttribute("innerHTML").equals("gmail.com")).findAny();
        Optional<WebElement> credentialUserName = notesList1.stream().filter(note -> note.getAttribute("innerHTML").equals("olukol")).findAny();
        Assertions.assertTrue(createdUrl.isPresent());
        Assertions.assertTrue(credentialUserName.isPresent());

        // edit credential
        editCredential(driver);
        // verify that credential was updated
        notesTable = driver.findElement(By.id("credentialTable"));
        notesList = notesTable.findElements(By.tagName("th"));
        notesList1 = notesTable.findElements(By.tagName("td"));
        Optional<WebElement> credentialCreatedUrl = notesList.stream().filter(note -> note.getAttribute("innerHTML").equals("new url")).findAny();
        Optional<WebElement> credentialCreatedUsername = notesList1.stream().filter(note -> note.getAttribute("innerHTML").equals("new username")).findAny();
        Assertions.assertTrue(credentialCreatedUrl.isPresent());
        Assertions.assertTrue(credentialCreatedUsername.isPresent());

        // delete credential
        deleteCredential(driver);
        // verify that credential was deleted
        notesTable = driver.findElement(By.id("userTable"));
        notesList = notesTable.findElements(By.tagName("th"));
        notesList1 = notesTable.findElements(By.tagName("td"));
        Optional<WebElement> credentialUrlElement = notesList.stream().filter(note -> note.getAttribute("innerHTML").equals("new url")).findAny();
        Optional<WebElement> credentialUsernameElement = notesList1.stream().filter(note -> note.getAttribute("innerHTML").equals("new username")).findAny();
        Assertions.assertTrue(credentialUrlElement.isEmpty());
        Assertions.assertTrue(credentialUsernameElement.isEmpty());
    }

    public void createNote(WebDriver driver,String username) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        //sign up
        doMockSignUp("ade", "dayo", username, "Olatejumi");
        //login
        doLogIn(username, "Olatejumi");
        //added note
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(30));

        WebElement newNote = driver.findElement(By.id("addNote"));
        wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(NOTE_TITLE);
        WebElement description = driver.findElement(By.id("note-description"));
        description.sendKeys(NOTE_DESCRIPTION);

        WebElement createdNote = driver.findElement(By.id("save-changes"));
        jse.executeScript("arguments[0].click();", createdNote);
        // result page
        Assertions.assertEquals("Result", driver.getTitle());
        //Check added note
        driver.get("http://localhost:" + this.port + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
    }

    public void editNote(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        createNote(webDriver,"editNote");

        WebElement notesTab = driver.findElement(By.id("edit-note-button"));
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(30));

        WebElement noteTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
        noteTitle.clear();
        noteTitle.sendKeys("new title");

        WebElement description = driver.findElement(By.id("note-description"));
        description.clear();
        description.sendKeys("new descriptions");

        WebElement createdNote = driver.findElement(By.id("save-changes"));
        jse.executeScript("arguments[0].click();", createdNote);

        Assertions.assertEquals("Result", driver.getTitle());
        //Check added note
        driver.get("http://localhost:" + this.port + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
    }

    public void deleteNote(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        createNote(webDriver,"deleteNote");

        WebElement notesTab = driver.findElement(By.id("delete-note"));
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(30));

        Assertions.assertEquals("Result", driver.getTitle());
        //List all notes
        driver.get("http://localhost:" + this.port + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
    }

    public void createCredential(WebDriver driver,String username) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        //sign up
		doMockSignUp("test","test",username,"test");
		doLogIn(username,"test");

        //added note
        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialTab);
        wait.withTimeout(Duration.ofSeconds(30));


        WebElement newCredential = driver.findElement(By.id("add-credential"));
        wait.until(ExpectedConditions.elementToBeClickable(newCredential)).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys("gmail.com");
        WebElement credUsername = driver.findElement(By.id("credential-username"));
        credUsername.sendKeys("olukol");

        WebElement description = driver.findElement(By.id("credential-password"));
        description.sendKeys("password");

        WebElement createdCredential = driver.findElement(By.id("save-credential-changes"));
        jse.executeScript("arguments[0].click();", createdCredential);
        // result page
        Assertions.assertEquals("Result", driver.getTitle());
        //Check added note
        driver.get("http://localhost:" + this.port + "/home");
        credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialTab);


    }

    public void editCredential(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
		createCredential(webDriver,"editCredential");

        WebElement credentialTab = driver.findElement(By.id("edit-credential-button"));
        jse.executeScript("arguments[0].click()", credentialTab);
        wait.withTimeout(Duration.ofSeconds(30));

        WebElement noteTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        noteTitle.clear();
        noteTitle.sendKeys("new url");

        WebElement description = driver.findElement(By.id("credential-username"));
        description.clear();
        description.sendKeys("new username");

        WebElement updatedCredential = driver.findElement(By.id("save-credential-changes"));
        jse.executeScript("arguments[0].click();", updatedCredential);

        Assertions.assertEquals("Result", driver.getTitle());
        //Check added note
        driver.get("http://localhost:" + this.port + "/home");
        credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialTab);


    }

    public void deleteCredential(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        createCredential(webDriver,"deleteCredential");

        WebElement credentialDeleteTab = driver.findElement(By.id("delete-credential"));
        jse.executeScript("arguments[0].click()", credentialDeleteTab);
        wait.withTimeout(Duration.ofSeconds(30));

        Assertions.assertEquals("Result", driver.getTitle());
        //List all notes
        driver.get("http://localhost:" + this.port + "/home");
        credentialDeleteTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialDeleteTab);
    }
}
