package bdd.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.MainPage;

import static org.testng.Assert.assertFalse;

public class MainSteps {

    MainPage page = new MainPage();

    @Given("user opens main page")
    public void user_opens_main_page() {
        page.open();
    }

    @Then("examples dropdown is visible")
    public void examples_dropdown_is_visible() {
        // Use the existing method to get dropdown items' text
        assertFalse(page.getDemosDropdownItemsText().isEmpty(), "Examples dropdown is not visible or has no items.");
    }
}