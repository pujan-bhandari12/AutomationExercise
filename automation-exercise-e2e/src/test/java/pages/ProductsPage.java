package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private BaseTest base;

    private By searchInput = By.id("search_product");
    private By searchBtn = By.id("submit_search");
    private By searchedHeader = By.xpath("//h2[text()='Searched Products']");
    private By productCards = By.cssSelector("div.productinfo");
    private By continueShoppingBtn = By.cssSelector("button.close-modal");
    private By viewCartModalLink = By.xpath("//u[text()='View Cart']");

    public ProductsPage(WebDriver driver, WebDriverWait wait, BaseTest base) {
        this.driver = driver;
        this.wait = wait;
        this.base = base;
    }

    public void searchProduct(String keyword) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(keyword);
        base.safeClick(searchBtn);
    }

    public int getSearchedProductsCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchedHeader));
        return driver.findElements(productCards).size();
    }

    public void viewFirstProductDetails() {
        base.safeClick(By.cssSelector("a[href='/product_details/1']"));
        base.handleVignetteAd();
    }

    public void addProductToCartAndContinue(String productId) {
        base.safeClick(By.cssSelector("a[data-product-id='" + productId + "']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueShoppingBtn));
        base.safeClick(continueShoppingBtn);
    }

    public void addProductToCartAndViewCart(String productId) {
        base.safeClick(By.cssSelector("a[data-product-id='" + productId + "']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewCartModalLink));
        base.safeClick(viewCartModalLink);
    }

    public void filterByWomenDress() {
        base.safeClick(By.cssSelector("a[href='#Women']"));
        base.safeClick(By.cssSelector("a[href='/category_products/1']"));
        base.handleVignetteAd();
    }

    public void filterByBrandPolo() {
        base.safeClick(By.cssSelector("a[href='/brand_products/Polo']"));
        base.handleVignetteAd();
    }

    public String getPageHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title.text-center"))).getText();
    }
}
