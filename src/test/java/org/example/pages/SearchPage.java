package org.example.pages;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class SearchPage extends PageObject {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css="input[name='search']")
    WebElementFacade searchTextField;

    @Step("Enter item name and click enter")
    public void enterItemName(String item) {
     searchTextField.sendKeys(item , Keys.ENTER);
    }

    @Step("Get search results list")
    public List<String> getSearchResultsList() {
       return $$(By.cssSelector("div[class='product-thumb'] h4 a")).textContents();
    }

}
