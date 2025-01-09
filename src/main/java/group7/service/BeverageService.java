package group7.service;

import group7.dto.*;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;

import java.util.List;

public interface BeverageService {

    Beverage createBeverage(BeverageCreateDto beverageCreateDto);

    List<Beverage> getSoldBeverages();

    List<Beverage> getAllBeverages();

    public List<BeverageResponseDto> getAllBeveragesWithDetails();

    PaginatedResponseDto<Beverage> getAllBeveragesPaginatedOld(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    PaginatedResponseDto<BeverageResponseDto> getAllBeveragesPaginated(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<Beverage> getAlcoholicBeverages();

    Beverage createCrate(BeverageCreateDto beverageCreateDto);
    Beverage createBottle(BeverageCreateDto bottleCreateDto);

    BeverageResponseDto findBeverageById(Long id);

    List<Bottle> getAllBottles();
    List<Crate> getAllCrates();

    Void deleteBeverage(Long id);
    BeverageResponseDto updateBeverage(BeverageCreateDto beverageCreateDto, Long beverageId);
    Beverage update(Beverage beverage);

    //updates stock of a bunch of stock, they are in the process of ordering
    boolean updateStock();

    Beverage getBeverageById(Long id);
}
