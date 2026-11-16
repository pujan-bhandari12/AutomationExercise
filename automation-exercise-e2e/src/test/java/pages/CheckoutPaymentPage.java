package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class CheckoutPaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private BaseTest base;

    private By deliveryAddressBox = By.id("address_delivery");
    private By commentTextArea = By.name("message");
    private By placeOrderBtn = By.cssSelector("a[href='/payment']");

    private By nameOnCardInput = By.name("name_on_card");
    private By cardNumberInput = By.name("card_number");
    private By cvcInput = By.name("cvc");
    private By expiryMonthInput = By.name("expiry_month");
    private By expiryYearInput = By.name("expiry_year");
    private By payConfirmBtn = By.id("submit");

    private By orderPlacedHeading = By.cssSelector("h2[data-qa='order-placed']");
    private By downloadInvoiceBtn = By.cssSelector("a.check_out");
    private By continueBtn = By.cssSelector("a[data-qa='continue-button']");

    public CheckoutPaymentPage(WebDriver driver, WebDriverWait wait, BaseTest base) {
        this.driver = driver;
        this.wait = wait;
        this.base = base;
    }

    public String getDeliveryAddressText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryAddressBox)).getText();
    }

    public void enterCommentAndPlaceOrder(String comment) {
        driver.findElement(commentTextArea).sendKeys(comment);
        base.safeClick(placeOrderBtn);
        base.handleVignetteAd();
    }

    public String enterPaymentAndConfirm(String name, String cardNo, String cvc, String month, String year) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameOnCardInput)).sendKeys(name);
        driver.findElement(cardNumberInput).sendKeys(cardNo);
        driver.findElement(cvcInput).sendKeys(cvc);
        driver.findElement(expiryMonthInput).sendKeys(month);
        driver.findElement(expiryYearInput).sendKeys(year);
        base.safeClick(payConfirmBtn);

        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderPlacedHeading)).getText();
    }

    public void downloadInvoiceAndContinue() {
        String currentWindow = driver.getWindowHandle();
        base.safeClick(downloadInvoiceBtn);
        Set<String> openWindows = driver.getWindowHandles();
        if (openWindows.contains(currentWindow)) {
            driver.switchTo().window(currentWindow);
        } else if (!openWindows.isEmpty()) {
            driver.switchTo().window(openWindows.iterator().next());
        }
        base.safeClick(continueBtn);
        base.handleVignetteAd();
    }
}
