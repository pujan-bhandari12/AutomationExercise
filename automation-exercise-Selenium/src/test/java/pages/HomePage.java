package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private BaseTest base;

    private By signupLoginLink = By.cssSelector("a[href='/login']");
    private By logoutLink = By.cssSelector("a[href='/logout']");
    private By deleteAccountLink = By.cssSelector("a[href='/delete_account']");
    private By productsLink = By.cssSelector("a[href='/products']");
    private By cartLink = By.cssSelector("a[href='/view_cart']");
    private By testCasesLink = By.cssSelector("a[href='/test_cases']");
    private By apiTestingLink = By.cssSelector("a[href='/api_list']");
    private By contactUsLink = By.cssSelector("a[href='/contact_us']");
    private By featuresItems = By.cssSelector("div.features_items");
    private By loggedInBanner = By.xpath("//a[contains(text(),'Logged in as')]");
    private By subscribeEmailInput = By.id("susbscribe_email");
    private By subscribeBtn = By.id("subscribe");
    private By subscribeSuccess = By.id("success-subscribe");
    private By scrollUpArrow = By.id("scrollUp");

    public HomePage(WebDriver driver, WebDriverWait wait, BaseTest base) {
        this.driver = driver;
        this.wait = wait;
        this.base = base;
    }

    public boolean isFeaturesVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(featuresItems)).isDisplayed();
    }

    public void goToLogin() {
        base.safeClick(signupLoginLink);
        base.handleVignetteAd();
    }

    public void goToProducts() {
        base.safeClick(productsLink);
        base.handleVignetteAd();
    }

    public void goToCart() {
        base.safeClick(cartLink);
        base.handleVignetteAd();
    }

    public void goToTestCases() {
        base.safeClick(testCasesLink);
        base.handleVignetteAd();
    }

    public void goToApiTesting() {
        base.safeClick(apiTestingLink);
        base.handleVignetteAd();
    }

    public void goToContactUs() {
        base.safeClick(contactUsLink);
        base.handleVignetteAd();
    }

    public String getLoggedInUserText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInBanner)).getText();
    }

    public void logout() {
        base.safeClick(logoutLink);
    }

    public void deleteAccount() {
        base.safeClick(deleteAccountLink);
    }

    public boolean subscribeInFooter(String email) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        driver.findElement(subscribeEmailInput).clear();
        driver.findElement(subscribeEmailInput).sendKeys(email);
        base.safeClick(subscribeBtn);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(subscribeSuccess)).isDisplayed();
    }

    public void clickScrollUpArrow() {
        base.safeClick(scrollUpArrow);
    }
}
