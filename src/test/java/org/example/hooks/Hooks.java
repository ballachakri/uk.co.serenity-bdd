package org.example.hooks;

import net.serenitybdd.core.pages.PageObject;
import org.junit.jupiter.api.BeforeEach;

public class Hooks extends PageObject {

    @BeforeEach
    public void setUpHomePage(){
        this.open();
        getDriver().manage().window().maximize();
    }
}
