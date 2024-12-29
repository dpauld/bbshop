package group7.service;

import group7.dto.AddBeverageRequestDTO;
import group7.dto.AddBottleRequestDTO;
import group7.dto.AddCrateRequestDTO;
import group7.dto.BeverageResponseDTO;
import group7.dto.BottleResponseDTO;
import group7.dto.CrateResponseDTO;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;

import java.util.List;

public interface BeverageService {

    BeverageResponseDTO beverageToBeverageResponseDTO(Beverage beverage);

    Beverage beverageRequestDTOToBeverage(AddBeverageRequestDTO addBeverageRequestDTO);

    List<BeverageResponseDTO> beveragesToBeverageResponseDTOList(List<Beverage> beverages);

    BeverageResponseDTO addBeverage(AddBeverageRequestDTO addBeverageRequestDTO);

    List<BeverageResponseDTO> getSoldBeverages();

    List<BeverageResponseDTO> getAllBeverages();

    List<BeverageResponseDTO> getAlcoholicBeverages();

    BeverageResponseDTO addCrate(AddCrateRequestDTO addCrateRequestDTO);
    BeverageResponseDTO addBottle(AddBottleRequestDTO addBottleRequestDTO);

    BeverageResponseDTO findBeveragesById(Long id);

    List<BottleResponseDTO> getAllBottles();

    List<CrateResponseDTO> getAllCrates();
}
