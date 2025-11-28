package org.example.tests;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.hooks.Hooks;
import org.example.steps.SearchSteps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class LoginTests extends Hooks{

    @Steps
    SearchSteps searchSteps;

    @Test
    @Tag("smoke")
    @Step("Search for Iphone")
    public void searchForIphone() throws InterruptedException {
        searchSteps.searchFor("iphone");
        searchSteps.verifySearchResultList("iphone");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    @Step("Search for Mac")
    public void searchForMac() throws InterruptedException {
        searchSteps.searchFor("Mac");
        searchSteps.verifySearchResultList("Mac");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    @Step("Search for Nikon camara")
    public void searchForCamara() throws InterruptedException {
        searchSteps.searchFor("Nikon");
        searchSteps.verifySearchResultList("Nikon");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName());
    }

}
