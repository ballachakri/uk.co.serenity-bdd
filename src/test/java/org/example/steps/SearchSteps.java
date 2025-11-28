package org.example.steps;

import net.serenitybdd.annotations.Step;
import org.assertj.core.api.Assertions;
import org.example.pages.SearchPage;

public class SearchSteps {


    SearchPage searchPage;

    @Step("Search for '{0}' from search page")
    public void searchFor(String item) {
        searchPage.enterItemName(item);
    }

    @Step("Verify see results for searched {0}item")
    public void verifySearchResultList(String item) {
        for (String itemName : searchPage.getSearchResultsList()) {

            Assertions.assertThat(itemName).contains(item);
        }
    }

}
