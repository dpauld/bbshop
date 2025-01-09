package group7.controller;

import org.springframework.ui.Model;

public interface AdminController {
    /**
     * Lists all available beverages.
     *
     * @param model The model to which the list of beverages is added.
     * @return The name of the view that displays the beverages list (e.g., "beverages").
     */
     public String getAllBeverages(Model model);

}
