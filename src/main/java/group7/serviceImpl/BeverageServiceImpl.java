package group7.serviceImpl;

import group7.dto.BeverageRequestDTO;
import group7.dto.BeverageResponseDTO;
import group7.entity.Beverage;

import java.util.List;

public interface BeverageServiceImpl {

    BeverageResponseDTO beverageToBeverageResponseDTO(Beverage beverage);

    Beverage beverageRequestDTOToBeverage(BeverageRequestDTO beverageRequestDTO);

    List<BeverageResponseDTO> beveragesToBeverageResponseDTOList(List<Beverage> beverages);

    BeverageResponseDTO addBeverage(BeverageRequestDTO beverageRequestDTO);

    List<BeverageResponseDTO> getSoldBeverages();

    List<BeverageRequestDTO> getDemoBeverages();

    List<BeverageResponseDTO> getAllBeverages();

    List<BeverageResponseDTO> getAlcoholicBeverages();

}
