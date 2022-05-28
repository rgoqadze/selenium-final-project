import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

import static java.lang.Thread.sleep;

public class FinalProject {
    WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) {
        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        }
    }

    @Test
    public void automationpractice() throws InterruptedException {
        driver.get("https://www.swoop.ge/");
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement rest = driver.findElement(By.cssSelector("#body > div.container-fluid.menu-outer > div > div > nav > ul > li.cat-2.cat > a > label"));
        actions.moveToElement(rest).build().perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[9]/div/div/nav/ul/li[4]/div/div/ul/li[12]/a")));
        driver.findElement(By.xpath("/html/body/div[9]/div/div/nav/ul/li[4]/div/div/ul/li[12]/a")).click();
        WebElement highprice = driver.findElement(By.xpath("//*[@id='price-slider-range']//a[2]"));
        actions.dragAndDropBy(highprice, -254, 0).build().perform();
        actions.moveToElement(driver.findElement(By.xpath("//div[@class='special-offer'][1]"))).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='special-offer']//div[@class='discounted-prices']//p[1]")));
        List<WebElement> vaucherprice = driver.findElements(By.xpath("//div[@class='special-offer']//div[@class='discounted-prices']//p[1]"));
        WebElement price;
        for (int i = 0; i < vaucherprice.size(); i++) {
            price = vaucherprice.get(i);
            int price1 = Integer.parseInt(price.getText().substring(0,2));
            if (price1 < 100) {
                System.out.println("Price < 100");
            } else {
                System.out.println("Price > 100");
            }
        }
        List<WebElement> cards = driver.findElements(By.cssSelector("div.special-offer"));
        WebElement card = cards.get(0);
        card.click();
        WebElement mainimage = driver.findElement(By.cssSelector("div.col-6"));
        mainimage.click();
        List<WebElement> img = driver.findElements(By.xpath("//div[@class='bottom-side-images']/div"));
        sleep(3000);
        int imgcount = img.size() + 2;
        for (int i = 0; i < imgcount; i++) {
            WebElement next = driver.findElement(By.className("lb-next"));
            actions.moveToElement(next).build().perform();
            next.click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'lb-number') and (text()='Image 21 of 21')]")));
        WebElement imgnumber = driver.findElement(By.xpath("//div[@class='lb-details']//span[2]"));
        if (imgnumber.getText().equals("Image 21 of 21")) {
            System.out.println("I clicked all images");
        } else {
            System.out.println("I failed");
        }
        WebElement close = driver.findElement(By.className("lb-close"));
        js.executeScript("arguments[0].click();", close);
        WebElement vaucherprice1 = driver.findElement(By.className("details-voucher-price"));
        int vaucherprice2 = Integer.parseInt(vaucherprice1.getText().substring(0, 1));
        driver.findElement(By.xpath("//button[contains(@type,'button')][2]")).click();
        int vaucherprice3 = Integer.parseInt(vaucherprice1.getText().substring(0, 2));
        int vaucherprice4 = (vaucherprice2 * 2);
        Assert.assertEquals(vaucherprice3, vaucherprice4);
        if (vaucherprice3 == vaucherprice4) {
            System.out.println("quantity increased");
        } else {
            System.out.println("Quantity does not increase");
        }
        js.executeScript("arguments[0].click();", vaucherprice1);
        String quantity = (String) js.executeScript("return document.getElementById('basket_item_count').innerText;");
        Assert.assertEquals(quantity,Integer.toString(2));
        String totalamount = (String) js.executeScript("return document.getElementById('basket-total-amount').innerText;");
        Assert.assertEquals(vaucherprice4+".00",totalamount);
        driver.findElement(By.cssSelector("a.totals__button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("register")));
        driver.findElement(By.className("register")).click();
        driver.findElement(By.className("juridial")).click();
        WebElement legalform = driver.findElement(By.id("lLegalForm"));
        Select select = new Select(legalform);
        select.selectByIndex(1);
        Faker faker = new Faker();
        driver.findElement(By.id("lName")).sendKeys(faker.funnyName().name());
        driver.findElement(By.id("lTaxCode")).sendKeys(faker.number().digits(9));
        WebElement date1 = driver.findElement(By.id("registred"));
        date1.sendKeys("12122012");
        driver.findElement(By.id("lAddress")).sendKeys(faker.address().fullAddress());
        WebElement country = driver.findElement(By.id("lCountryCode"));
        Select slct = new Select(country);
        slct.selectByIndex(10);
        driver.findElement(By.id("lCity")).sendKeys(faker.address().city());
        driver.findElement(By.id("lPostalCode")).sendKeys(faker.number().digits(4));
        driver.findElement(By.id("lWebSite")).sendKeys("www." + faker.animal().name()+".com");
        driver.findElement(By.id("lBank")).sendKeys(faker.name().name());
        driver.findElement(By.id("lBankAccount")).sendKeys("GE10TB"+faker.number().digits(16));
        driver.findElement(By.id("lContactPersonEmail")).sendKeys(faker.name().username()+"@gmail.com");
        int pasword = Integer.parseInt(faker.number().digits(9));
        driver.findElement(By.id("lContactPersonName")).sendKeys(faker.name().fullName());
        WebElement gender =  driver.findElement(By.id("lContactPersonGender"));
        Select sel = new Select(gender);
        sel.selectByIndex(1);
        WebElement date2 =  driver.findElement(By.id("birthday"));
        date2.sendKeys("12051994");
        WebElement citizen = driver.findElement(By.id("lContactPersonCountryCode"));
        Select sl = new Select(citizen);
        sl.selectByIndex(2);
        driver.findElement(By.id("lContactPersonPersonalID")).sendKeys(faker.idNumber().valid());
        driver.findElement(By.id("lContactPersonPhone")).sendKeys(faker.phoneNumber().phoneNumber());
        WebElement regitrationbutton = driver.findElement(By.xpath("//*[@id=\"register-content-2\"]/div[2]/a/div/input"));
        regitrationbutton.click();
        js.executeScript("window.scrollBy(0,500)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("legalInfoMassage")));
        WebElement errortext = driver.findElement(By.id("legalInfoMassage"));
        if (errortext.isDisplayed()) {
            System.out.println("რეგისტრაციის დროს დაფიქსირდა შეცდომა!");
        }
        else {
            System.out.println("element is not displayd");
        }
        driver.findElement(By.id("lContactPersonPassword")).sendKeys(Integer.toString(pasword));
        driver.findElement(By.id("lContactPersonConfirmPassword")).sendKeys(Integer.toString(pasword));
        regitrationbutton.click();

    }
    @AfterClass
    public void TearDowun() {
        driver.quit();
    }
}

