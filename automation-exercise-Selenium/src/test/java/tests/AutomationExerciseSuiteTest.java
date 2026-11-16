package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class AutomationExerciseSuiteTest extends BaseTest {

    private HomePage homePage;
    private LoginSignupPage loginSignupPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPaymentPage checkoutPaymentPage;

    private final String uniqueEmail = "pujan_qa_" + System.currentTimeMillis() + "@test.com";
    private final String userName = "Pujan QA";
    private final String password = "Pass@123";
    private final String address = "123 Balkumari St";

    @BeforeClass
    public void initPages() {
        homePage = new HomePage(driver, wait, this);
        loginSignupPage = new LoginSignupPage(driver, wait, this);
        productsPage = new ProductsPage(driver, wait, this);
        cartPage = new CartPage(driver, wait, this);
        checkoutPaymentPage = new CheckoutPaymentPage(driver, wait, this);
    }

    @Test(priority = 1, description = "TC_AE_001: Verify Home Page load")
    public void tc001_verifyHomePageLoad() {
        driver.get(BASE_URL);
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
        Assert.assertTrue(homePage.isFeaturesVisible());
    }

    @Test(priority = 2, description = "TC_AE_002 & TC_AE_003: Verify Signup Page & Register New User")
    public void tc003_verifyRegisterUser() {
        homePage.goToLogin();
        Assert.assertTrue(loginSignupPage.isSignupFormVisible());

        loginSignupPage.startSignup(userName, uniqueEmail);
        String createdMsg = loginSignupPage.completeRegistrationForm(password, "Pujan", "Bhandari", address);
        Assert.assertEquals(createdMsg, "ACCOUNT CREATED!");
        Assert.assertTrue(homePage.getLoggedInUserText().contains(userName));
    }

    @Test(priority = 3, description = "TC_AE_009, TC_AE_004, TC_AE_008, TC_AE_007: Logout, Negative Checks & Login")
    public void tc007_verifyAuthNegativeAndPositiveFlows() {
        // TC_AE_009: Logout
        homePage.logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));

        // TC_AE_004: Duplicate Email Signup
        loginSignupPage.startSignup(userName, uniqueEmail);
        Assert.assertTrue(loginSignupPage.isDuplicateEmailErrorVisible());

        // TC_AE_008: Invalid Login
        loginSignupPage.login("wrong_qa@test.com", "WrongPass123");
        Assert.assertTrue(loginSignupPage.isInvalidLoginErrorVisible());

        // TC_AE_007: Valid Login
        loginSignupPage.login(uniqueEmail, password);
        Assert.assertTrue(homePage.getLoggedInUserText().contains(userName));
    }

    @Test(priority = 4, description = "TC_AE_011 & TC_AE_012: Verify Test Cases & API Testing Navigation")
    public void tc011_verifyNavigationPages() {
        homePage.goToTestCases();
        Assert.assertTrue(driver.getCurrentUrl().contains("/test_cases"));

        homePage.goToApiTesting();
        Assert.assertTrue(driver.getCurrentUrl().contains("/api_list"));
    }

    @Test(priority = 5, description = "TC_AE_015, TC_AE_016, TC_AE_017, TC_AE_018, TC_AE_020: Products, Search & Filters")
    public void tc016_verifyProductsSearchAndFilters() {
        homePage.goToProducts();

        // TC_AE_017: Invalid Search
        productsPage.searchProduct("LaptopXYZ99");
        Assert.assertEquals(productsPage.getSearchedProductsCount(), 0);

        // TC_AE_016: Valid Search
        productsPage.searchProduct("Tshirt");
        Assert.assertTrue(productsPage.getSearchedProductsCount() > 0);

        // TC_AE_018: Category Filter (Women > Dress)
        productsPage.filterByWomenDress();
        Assert.assertTrue(productsPage.getPageHeading().toUpperCase().contains("WOMEN - DRESS PRODUCTS"));

        // TC_AE_020: Brand Filter (Polo)
        productsPage.filterByBrandPolo();
        Assert.assertTrue(productsPage.getPageHeading().toUpperCase().contains("BRAND - POLO PRODUCTS"));
    }

    @Test(priority = 6, description = "TC_AE_021: Verify Submit Product Review")
    public void tc021_verifyProductReview() {
        homePage.goToProducts();
        productsPage.viewFirstProductDetails();

        driver.findElement(By.id("name")).sendKeys(userName);
        driver.findElement(By.id("email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("review")).sendKeys("Great fabric quality and fast delivery!");
        safeClick(By.id("button-review"));

        Assert.assertTrue(driver.findElement(By.cssSelector("#review-section .alert-success")).isDisplayed());
    }

    @Test(priority = 7, description = "TC_AE_023, TC_AE_024, TC_AE_026: Add Multiple Products & Remove from Cart")
    public void tc024_verifyCartOperations() {
        homePage.goToProducts();
        productsPage.addProductToCartAndContinue("1"); // Blue Top
        productsPage.addProductToCartAndContinue("2"); // Men Tshirt
        productsPage.addProductToCartAndViewCart("3"); // Sleeveless Dress

        Assert.assertTrue(cartPage.isProductInCart("1"));
        Assert.assertTrue(cartPage.isProductInCart("2"));
        Assert.assertTrue(cartPage.isProductInCart("3"));

        // TC_AE_026: Remove item 3
        cartPage.removeProduct("3");
    }

    @Test(priority = 8, description = "TC_AE_030 & TC_AE_032: E2E Place Order & Download Invoice")
    public void tc030_verifyEndToEndOrderPlacement() {
        cartPage.proceedToCheckout();
        Assert.assertTrue(checkoutPaymentPage.getDeliveryAddressText().contains(address));

        checkoutPaymentPage.enterCommentAndPlaceOrder("Deliver between 10 AM and 5 PM");
        String orderStatus = checkoutPaymentPage.enterPaymentAndConfirm(
                userName, "4111111111111111", "123", "12", "2028");
        Assert.assertEquals(orderStatus, "ORDER PLACED!");

        // TC_AE_032: Download Invoice
        checkoutPaymentPage.downloadInvoiceAndContinue();
    }

    @Test(priority = 9, description = "TC_AE_033 & TC_AE_035: Footer Subscription & Scroll Up")
    public void tc033_verifyFooterSubscriptionAndScrollUp() {
        Assert.assertTrue(homePage.subscribeInFooter(uniqueEmail));
        homePage.clickScrollUpArrow();
    }

    @Test(priority = 10, description = "TC_AE_010: Delete Account Cleanup")
    public void tc010_verifyDeleteAccountCleanup() {
        homePage.deleteAccount();
        String deletedMsg = loginSignupPage.confirmAccountDeletedAndContinue();
        Assert.assertEquals(deletedMsg, "ACCOUNT DELETED!");
    }
}
