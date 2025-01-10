package group7.service;

import group7.dto.*;
        import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import group7.exception.InsufficientStockException;
import group7.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interface defining methods for managing beverages (bottles and crates).
 */
public interface BeverageService {

    /**
     * Creates a new beverage (bottle or crate) based on the provided information in the `BeverageCreateDto` object.
     *
     * @param beverageCreateDto The DTO containing details about the beverage to create.
     * @return The created Beverage object.
     * @throws ResourceNotFoundException if the referenced bottle (for crate creation) is not found.
     */
    Beverage createBeverage(BeverageCreateDto beverageCreateDto) throws ResourceNotFoundException;

    /**
     * Retrieves a list of all beverages (bottles and crates) from the database.
     *
     * @return A list of all {@link Beverage} objects.
     */
    List<Beverage> getAllBeverages();

    /**
     * Retrieves a list of all beverages (bottles and crates) with additional details mapped to {@link BeverageResponseDto} objects.
     * This includes information specific to bottles (volume, alcoholic, volume percent, supplier) and crates (number of bottles).
     *
     * @return A list of {@link BeverageResponseDto} objects containing detailed beverage information.
     */
    List<BeverageResponseDto> getAllBeveragesWithDetails();

    /**
     * Retrieves a paginated list of all beverages (bottles and crates) with sorting capabilities.
     * This method utilizes a more robust approach with detailed response DTOs.
     *
     * @param pageNumber The desired page number (zero-based indexing).
     * @param pageSize   The number of items per page.
     * @param sortBy     The field to sort by (e.g., "name", "price").
     * @param sortDir    The sorting direction ("asc" for ascending, "desc" for descending).
     * @return A {@link PaginatedResponseDto} object containing the paginated list of {@link BeverageResponseDto} objects and pagination details.
     * @throws ResourceNotFoundException if invalid parameters are provided.
     */
    PaginatedResponseDto<BeverageResponseDto> getAllBeveragesPaginated(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) throws ResourceNotFoundException;

    /**
     * Retrieves a list of all alcoholic beverages (bottles only).
     *
     * @return A list of {@link Beverage} objects representing alcoholic beverages (bottles).
     */
    List<Beverage> getAlcoholicBeverages();

    /**
     * Creates a new crate based on the provided information in the {@link BeverageCreateDto} object.
     * This method also retrieves the referenced bottle information from the database.
     *
     * @param beverageCreateDto The DTO containing details about the crate to create, including the referenced bottle ID.
     * @return The created {@link Crate} object.
     * @throws ResourceNotFoundException if the referenced bottle is not found.
     */
    Crate createCrate(BeverageCreateDto beverageCreateDto) throws ResourceNotFoundException;

    /**
     * Creates a new bottle based on the provided information in the {@link BeverageCreateDto} object.
     *
     * @param beverageCreateDto The DTO containing details about the bottle to create.
     * @return The created {@link Bottle} object.
     */
    Bottle createBottle(BeverageCreateDto beverageCreateDto);

    /**
     * Retrieves a beverage by its ID.
     *
     * @param id The ID of the beverage to retrieve.
     * @return The {@link BeverageResponseDto} object, or null if not found.
     * @throws ResourceNotFoundException if the beverage with the given ID is not found.
     */
    BeverageResponseDto findBeverageById(Long id) throws ResourceNotFoundException;

    /**
     * Retrieves a list of all bottles.
     *
     * @return A list of {@link Bottle} objects.
     */
    List<Bottle> getAllBottles();

    /**
     * Retrieves a list of all crates.
     *
     * @return A list of {@link Crate} objects.
     */
    List<Crate> getAllCrates();

    /**
     * Deletes a beverage by its ID.
     *
     * @param id The ID of the beverage to delete.
     * @throws ResourceNotFoundException if the beverage with the given ID is not found.
     */
    void deleteBeverage(Long id) throws ResourceNotFoundException;

    /**
     * Updates an existing beverage (bottle or crate) based on the provided information.
     *
     * @param beverageCreateDto The DTO containing the updated beverage details.
     * @param beverageId        The ID of the beverage to update.
     * @return The updated {@link BeverageResponseDto} object.
     * @throws ResourceNotFoundException if the beverage with the given ID is not found.
     */
    BeverageResponseDto updateBeverage(BeverageCreateDto beverageCreateDto, Long beverageId) throws ResourceNotFoundException;

    /**
     * Updates an existing beverage entity.
     * @param beverage the beverage to update
     * @return the updated beverage
     */
    Beverage update(Beverage beverage);

    /**
     * Updates the stock of beverages after an order is placed.
     *
     * @return true if the stock update is successful, false otherwise.
     * @throws InsufficientStockException if there is not enough stock for any of the beverages in the order.
     * @throws ResourceNotFoundException if a beverage is not found.
     */
    boolean updateStock() throws InsufficientStockException, ResourceNotFoundException;

    /**
     * Retrieves a beverage entity by its ID.
     *
     * @param id The ID of the beverage to retrieve.
     * @return The {@link Beverage} object, or null if not found.
     * @throws ResourceNotFoundException if the beverage with the given ID is not found.
     */
    Beverage getBeverageById(Long id) throws ResourceNotFoundException;

   /**
     * Retrieves a Bottle by ID and maps it to a BeverageResponseDto.
     * Throws ResourceNotFoundException if not found or if the beverage is not a Bottle.
     *
     * @param id The ID of the Bottle.
     * @return The BeverageResponseDto for the Bottle.
     * @throws ResourceNotFoundException If the Bottle is not found.
     */
    BeverageResponseDto findBottleById(Long id) throws ResourceNotFoundException;

    /**
     * Retrieves a Crate by ID and maps it to a BeverageResponseDto (with type set to "crate").
     * Throws ResourceNotFoundException if not found or if the beverage is not a Crate.
     *
     * @param id The ID of the Crate.
     * @return The BeverageResponseDto for the Crate.
     * @throws ResourceNotFoundException If the Crate is not found.
     */
    BeverageResponseDto findCrateById(Long id) throws ResourceNotFoundException;
}
