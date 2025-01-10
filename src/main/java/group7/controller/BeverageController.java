package group7.controller;

import group7.dto.*;
import group7.entity.Bottle;
import group7.entity.Crate;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * This interface defines the API for a Beverage Controller, specifying the methods
 * available for managing beverages (bottles and crates), including CRUD operations,
 * retrieval, and pagination functionalities.
 */
public interface BeverageController {

    /**
     * Retrieves all beverages with pagination options.
     *
     * @param model      The Spring {@code Model} object.
     * @param pageNumber The current page number.
     * @param pageSize   The number of beverages per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sort direction.
     * @return A view name.
     */
    String getAllBeveragesPaginated(Model model, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    /**
     * Retrieves a specific beverage by its ID.
     *
     * @param id      The ID of the beverage.
     * @param session The HttpSession object.
     * @param model   The Spring {@code Model} object.
     * @return A view name.
     */
    String getBeverageById(Long id, HttpSession session, Model model);

    /**
     * Retrieves all crates in JSON format.
     *
     * @param model The Spring {@code Model} object.
     * @return A ResponseEntity containing a list of {@code Crate} objects.
     */
    ResponseEntity<List<Crate>> getAllCratesJson(Model model);

    /**
     * Retrieves all bottles in JSON format.
     *
     * @param model The Spring {@code Model} object.
     * @return A ResponseEntity containing a list of {@code Bottle} objects.
     */
    ResponseEntity<List<Bottle>> getAllBotllesJson(Model model);

    /**
     * Creates a new bottle beverage.
     *
     * @param beverageCreateDto The DTO containing the bottle creation details.
     * @param errors            The Spring {@code Errors} object for validation.
     * @param model             The Spring {@code Model} object.
     * @return A redirect view name.
     */
    String createBottle(BeverageCreateDto beverageCreateDto, Errors errors, Model model, RedirectAttributes redirectAttributes);

    /**
     * Creates a new crate beverage.
     *
     * @param beverageCreateDto The DTO containing the crate creation details.
     * @param errors            The Spring {@code Errors} object for validation.
     * @param model             The Spring {@code Model} object.
     * @return A redirect view name.
     */
    String createCrate(BeverageCreateDto beverageCreateDto, Errors errors, Model model, RedirectAttributes redirectAttributes);

    /**
     * Deletes a beverage by its ID.
     *
     * @param id      The ID of the beverage to delete.
     * @param session The HttpSession object.
     * @return A redirect view name.
     */
    String deleteBeverage(Long id, HttpSession session, RedirectAttributes redirectAttributes);

    /**
     * Displays the update form for a beverage.
     * @param beverageId the id of the beverage
     * @param model the model
     * @return the view name
     */
    String showUpdateForm(Long beverageId, Model model);

    /**
     * Updates an existing bottle beverage.
     *
     * @param id                The ID of the bottle to update.
     * @param beverageCreateDto The DTO containing the updated bottle details.
     * @param model             The Spring {@code Model} object.
     * @return A redirect view name.
     */
    String updateBottle(Long id, BeverageCreateDto beverageCreateDto, Model model, RedirectAttributes redirectAttributes);

    /**
     * Updates an existing crate beverage.
     *
     * @param id                The ID of the crate to update.
     * @param beverageCreateDto The DTO containing the updated crate details.
     * @param model             The Spring {@code Model} object.
     * @return A redirect view name.
     */
    String updateCrate(Long id, BeverageCreateDto beverageCreateDto, Model model, RedirectAttributes redirectAttributes);
}