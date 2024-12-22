package group7.service;

import group7.dto.AddBeverageRequestDTO;
import group7.dto.BeverageResponseDTO;
import group7.entity.Beverage;

import java.util.List;

public interface BeverageService {

    BeverageResponseDTO beverageToBeverageResponseDTO(Beverage beverage);

    Beverage beverageRequestDTOToBeverage(AddBeverageRequestDTO addBeverageRequestDTO);

    List<BeverageResponseDTO> beveragesToBeverageResponseDTOList(List<Beverage> beverages);

    BeverageResponseDTO addBeverage(AddBeverageRequestDTO addBeverageRequestDTO);

    List<BeverageResponseDTO> getSoldBeverages();

    List<BeverageResponseDTO> getDemoBeverages();

    List<BeverageResponseDTO> getAllBeverages();

    List<BeverageResponseDTO> getAlcoholicBeverages();

    List<BeverageResponseDTO> getAllCrates();

    List<BeverageResponseDTO> getAllBottles();

    BeverageResponseDTO findBeveragesById(Long id);
}
