package group7.service;

import group7.dto.BeverageRequestDTO;
import group7.dto.BeverageResponseDTO;
import group7.dto.BottleResponseDTO;
import group7.dto.CrateResponseDTO;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;

import java.util.List;

public interface BeverageService {

    BeverageResponseDTO beverageToBeverageResponseDTO(Beverage beverage);

    BottleResponseDTO bottleToBottleResponseDTO(Bottle bottle);

    CrateResponseDTO crateToCrateResponseDTO(Crate crate);

    Beverage beverageRequestDTOToBeverage(BeverageRequestDTO beverageRequestDTO);

    List<BeverageResponseDTO> beveragesToBeverageResponseDTOList(List<Beverage> beverages);

    List<BottleResponseDTO> bottlesToBottleResponseDTOList(List<Bottle> bottles);

    List<CrateResponseDTO> cratesToCrateResponseDTOList(List<Crate> crates);

    BeverageResponseDTO addBeverage(BeverageRequestDTO beverageRequestDTO);

    List<BeverageResponseDTO> getSoldBeverages();

    List<BeverageRequestDTO> getDemoBeverages();

    List<BeverageResponseDTO> getAllBeverages();

    List<BeverageResponseDTO> getAlcoholicBeverages();

    List<BottleResponseDTO> getAllBottles();

    List<CrateResponseDTO> getAllCrates();
}
