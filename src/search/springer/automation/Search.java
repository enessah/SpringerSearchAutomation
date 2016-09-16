package search.springer.automation;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Search {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://link.springer.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/");
  }

  /**
   * Validate SearchBox is displayed correctly.
   */
  @Test
  public void testSearchBoxExists() throws Exception {
    assertEquals("Submit", driver.findElement(By.id("search")).getAttribute("value"));
    assertEquals("", driver.findElement(By.id("search")).getText());
  }
  
  /**
   * Validate Search Options Drop Down button is displayed correctly.
   */
  @Test
  public void testOpenSearchOptionsButtonDropDown() throws Exception {
	  
    assertEquals("Search Options", driver.findElement(By.xpath("//div[@id='search-options']/button")).getText());
  }
  
  /**
   * Validate Advanced Search Link is displayed correctly in search options Drop-Down button.
   */
  @Test
  public void testAdvancedSearchLinkExists() throws Exception {
    
    driver.findElement(By.cssSelector("button.pillow-btn.open-search-options")).click();
    assertEquals("Advanced Search", driver.findElement(By.id("advanced-search-link")).getText());
  }
  
  /**
   * Validate Advanced Search Link is navigating to the right page.
   */
  @Test
  public void testAdvancedSearchLinkPage() throws Exception {
   
    driver.findElement(By.cssSelector("button.pillow-btn.open-search-options")).click();
    driver.findElement(By.id("advanced-search-link")).click();
    assertEquals("Home", driver.findElement(By.xpath("(//a[contains(@href, '/')])[7]")).getText());
    assertEquals("Contact Us", driver.findElement(By.xpath("//a[contains(@href, '/contactus')]")).getText());
    assertEquals("Advanced Search", driver.findElement(By.xpath("//div[@id='kb-nav--main']/h1")).getText());
    assertEquals("Find Resources", driver.findElement(By.xpath("//form[@id='advanced-search-form']/div/h2")).getText());
    assertTrue(isElementPresent(By.id("submit-advanced-search")));
  }
  
  /**
   * Validate Search Help Link is displayed correctly in search options Drop-Down button.
   */
  @Test
  public void testSearchHelpLinkExists() throws Exception {
    
    driver.findElement(By.cssSelector("button.pillow-btn.open-search-options")).click();
    assertEquals("Search Help", driver.findElement(By.id("search-help-link")).getText());
  }
  
  /**
   * Validate Search Help Link is navigating to the right page
   */
  @Test
  public void testSearchHelpLinkPage() throws Exception {
    
    driver.findElement(By.cssSelector("button.pillow-btn.open-search-options")).click();
    driver.findElement(By.id("search-help-link")).click();
    assertEquals("Home", driver.findElement(By.xpath("(//a[contains(@href, '/')])[7]")).getText());
    assertEquals("Contact Us", driver.findElement(By.xpath("//a[contains(@href, '/contactus')]")).getText());
    assertEquals("Search Tips", driver.findElement(By.xpath("//div[@id='information']/h1")).getText());
    assertEquals("Narrowing your results Top", driver.findElement(By.xpath("//div[@id='information']/h2")).getText());
    assertEquals("Start a new search Top", driver.findElement(By.xpath("//div[@id='information']/h2[2]")).getText());
    assertEquals("Language and stemming Top", driver.findElement(By.xpath("//div[@id='information']/h2[3]")).getText());
  }
  
  /**
   * Validate Searching existing item returns results of the element
   */

  @Test
  public void testSearchExistingItem() throws Exception {
    
    driver.findElement(By.id("query")).clear();
    driver.findElement(By.id("query")).sendKeys("nature");
    driver.findElement(By.id("search")).click();
    assertEquals("'nature'", driver.findElement(By.xpath("//div[@id='kb-nav--main']/div/h1/strong[2]")).getText());
    assertTrue(isElementPresent(By.xpath("//div[@id='kb-nav--main']/div/h1/strong[2]")));
    assertEquals("Nature", driver.findElement(By.linkText("Nature")).getText());
  }
  
  /**
   * Validate New Search button exists after searching an item 
   * and behaves correctly.
   */
  @Test
  public void testNewSearchButtonAfterSearchingAnItem() throws Exception {
    
    driver.findElement(By.id("query")).clear();
    driver.findElement(By.id("query")).sendKeys("nature");
    driver.findElement(By.id("search")).click();
    assertEquals("New Search", driver.findElement(By.id("global-search-new")).getText());
    driver.findElement(By.id("global-search-new")).click();
    assertEquals("", driver.findElement(By.id("query")).getText());
  }
  

  /**
   * Validate Searching non existing item returns 0 results of the element
   */
  @Test
  public void testSearchNonExistingItem() throws Exception {
    
    driver.findElement(By.id("query")).clear();
    driver.findElement(By.id("query")).sendKeys("@");
    driver.findElement(By.id("search")).click();
    
    // 0 Results are returned and the correct Message is displayed to user
    assertEquals("0", driver.findElement(By.cssSelector("strong")).getText());
    assertEquals("Sorry – we couldn’t find what you are looking for. Why not...", driver.findElement(By.cssSelector("h2")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

}
