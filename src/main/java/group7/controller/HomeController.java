package group7.controller;

import org.springframework.ui.Model;
/**
 * This interface defines methods for the Home Controller.
 */
public interface HomeController {

    /**
     * Handles the GET request for the home page.
     * Retrieves a list of alcoholic beverages, crates, and bottles from the service layer,
     * adds them to the model, and returns the name of the home view template.
     *
     * @param model the model to add attributes to
     * @return the name of the home view template
     */
    public String homePage(Model model);
}
