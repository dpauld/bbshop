package group7.controller;

import group7.dto.BeverageResponseDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;

public interface BeverageController {

    /**
     * Lists all available beverages.
     *
     * @param model The model to which the list of beverages is added.
     * @return The name of the view that displays the beverages list (e.g., "beverages").
     */
    public String getAllBeverages(Model model);

    /**
     * Fetches the list of demo beverages.
     *
     * @return A list of BeverageResponseDTO representing demo beverages.
     */
    public List<BeverageResponseDTO> getDemoBeverages();

    /**
     * Adds a beverage to the basket.
     *
     * @param id The ID of the beverage to add to the basket.
     * @param session The session used to display messages or errors.
     * @return The name of the page to redirect to after adding the beverage to the basket.
     */
    public String addBeverageToBasket(Long id, HttpSession session);
}
