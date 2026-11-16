package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private BaseTest base;

    private By proceedToCheckoutBtn = By.cssSelector("a.check_out");

    public CartPage(WebDriver driver, WebDriverWait wait, BaseTest base) {
        this.driver = driver;
        this.wait = wait;
        this.base = base;
    }

    public boolean isProductInCart(String productId) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-" + productId))).isDisplayed();
    }

    public void removeProduct(String productId) {
        base.safeClick(By.cssSelector("a.cart_quantity_delete[data-product-id='" + productId + "']"));
    }

    public void proceedToCheckout() {
        base.safeClick(proceedToCheckoutBtn);
    }
}
