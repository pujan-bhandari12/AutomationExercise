package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginSignupPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private BaseTest base;

    private By signupHeading = By.xpath("//h2[text()='New User Signup!']");
    private By signupNameInput = By.cssSelector("input[data-qa='signup-name']");
    private By signupEmailInput = By.cssSelector("input[data-qa='signup-email']");
    private By signupBtn = By.cssSelector("button[data-qa='signup-button']");
    private By duplicateEmailError = By.xpath("//p[text()='Email Address already exist!']");

    private By loginEmailInput = By.cssSelector("input[data-qa='login-email']");
    private By loginPasswordInput = By.cssSelector("input[data-qa='login-password']");
    private By loginBtn = By.cssSelector("button[data-qa='login-button']");
    private By invalidLoginError = By.xpath("//p[text()='Your email or password is incorrect!']");

    private By accountCreatedHeader = By.cssSelector("h2[data-qa='account-created']");
    private By accountDeletedHeader = By.cssSelector("h2[data-qa='account-deleted']");
    private By continueBtn = By.cssSelector("a[data-qa='continue-button']");

    public LoginSignupPage(WebDriver driver, WebDriverWait wait, BaseTest base) {
        this.driver = driver;
        this.wait = wait;
        this.base = base;
    }

    public boolean isSignupFormVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(signupHeading)).isDisplayed();
    }

    public void startSignup(String name, String email) {
        driver.findElement(signupNameInput).clear();
        driver.findElement(signupNameInput).sendKeys(name);
        driver.findElement(signupEmailInput).clear();
        driver.findElement(signupEmailInput).sendKeys(email);
        base.safeClick(signupBtn);
    }

    public String completeRegistrationForm(String password, String firstName, String lastName, String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender1"))).click();
        driver.findElement(By.id("password")).sendKeys(password);
        new Select(driver.findElement(By.id("days"))).selectByValue("16");
        new Select(driver.findElement(By.id("months"))).selectByValue("12");
        new Select(driver.findElement(By.id("years"))).selectByValue("2004");

        driver.findElement(By.id("first_name")).sendKeys(firstName);
        driver.findElement(By.id("last_name")).sendKeys(lastName);
        driver.findElement(By.id("address1")).sendKeys(address);
        new Select(driver.findElement(By.id("country"))).selectByVisibleText("India");
        driver.findElement(By.id("state")).sendKeys("Bagmati");
        driver.findElement(By.id("city")).sendKeys("Kathmandu");
        driver.findElement(By.id("zipcode")).sendKeys("44600");
        driver.findElement(By.id("mobile_number")).sendKeys("9800000000");

        base.safeClick(By.cssSelector("button[data-qa='create-account']"));
        String msg = wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedHeader)).getText();
        base.safeClick(continueBtn);
        base.handleVignetteAd();
        return msg;
    }

    public boolean isDuplicateEmailErrorVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(duplicateEmailError)).isDisplayed();
    }

    public void login(String email, String password) {
        driver.findElement(loginEmailInput).clear();
        driver.findElement(loginEmailInput).sendKeys(email);
        driver.findElement(loginPasswordInput).clear();
        driver.findElement(loginPasswordInput).sendKeys(password);
        base.safeClick(loginBtn);
    }

    public boolean isInvalidLoginErrorVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(invalidLoginError)).isDisplayed();
    }

    public String confirmAccountDeletedAndContinue() {
        String msg = wait.until(ExpectedConditions.visibilityOfElementLocated(accountDeletedHeader)).getText();
        base.safeClick(continueBtn);
        base.handleVignetteAd();
        return msg;
    }
}
