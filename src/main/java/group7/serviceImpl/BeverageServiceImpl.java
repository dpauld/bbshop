package group7.serviceImpl;

import group7.dto.*;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import group7.repository.BeverageRepository;
import group7.service.BeverageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeverageServiceImpl implements BeverageService {

    private final ModelMapper modelMapper;
    private final BeverageRepository beverageRepository;

    @Autowired
    public BeverageServiceImpl(ModelMapper modelMapper, BeverageRepository beverageRepository) {
        this.modelMapper = modelMapper;
        this.beverageRepository = beverageRepository;
    }

    @Override
    public BeverageResponseDTO beverageToBeverageResponseDTO(Beverage beverage) {
        return modelMapper.map(beverage, BeverageResponseDTO.class);
    }

    @Override
    public BottleResponseDTO bottleToBottleResponseDTO(Bottle bottle) {
        return modelMapper.map(bottle, BottleResponseDTO.class);
    }

    @Override
    public CrateResponseDTO crateToCrateResponseDTO(Crate crate) {
        return modelMapper.map(crate, CrateResponseDTO.class);
    }

    @Override
    public Beverage beverageRequestDTOToBeverage(BeverageRequestDTO beverageRequestDTO) {
        return modelMapper.map(beverageRequestDTO, Beverage.class);
    }

    @Override
    public List<BeverageResponseDTO> beveragesToBeverageResponseDTOList(List<Beverage> beverages) {
        return beverages.stream()
                .map(this::beverageToBeverageResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BottleResponseDTO> bottlesToBottleResponseDTOList(List<Bottle> bottles) {
        return bottles.stream()
                .map(this::bottleToBottleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CrateResponseDTO> cratesToCrateResponseDTOList(List<Crate> crates) {
        return crates.stream()
                .map(this::crateToCrateResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BeverageResponseDTO addBeverage(BeverageRequestDTO beverageRequestDTO) {
        Beverage savedBeverage = beverageRepository.save(beverageRequestDTOToBeverage(beverageRequestDTO));
        return beverageToBeverageResponseDTO(savedBeverage);
    }

    @Override
    public List<BeverageResponseDTO> getSoldBeverages() {
        return List.of();
    }

    @Override
    public List<BeverageRequestDTO> getDemoBeverages() {
        BottleRequestDTO bottleRequestDTO = new BottleRequestDTO("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIxheN74qXKhIgyRTVf_w67JIX4bTmzSvEFQ&s",1.5,false, 2.5,"Coca Cola" , 5);
            return List.of(
                    new BottleRequestDTO("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIxheN74qXKhIgyRTVf_w67JIX4bTmzSvEFQ&s",1.5,false, 2.5,"Coca Cola" , 5),
                    new CrateRequestDTO("https://image.invaluable.com/housePhotos/abell/07/764507/H0068-L364202388.JPG", 3, 10, 0L)  // Fixme change to BeverageResponseDTO
            );

    }

    @Override
    public List<BeverageResponseDTO> getAllBeverages() {
        return beveragesToBeverageResponseDTOList(beverageRepository.findAll()) ;
    }

    @Override
    public List<BeverageResponseDTO> getAlcoholicBeverages() {
        List<? extends Beverage> beverages = beverageRepository.findAllBottles();

        return beverages.stream()
                .filter(beverage -> beverage instanceof Bottle)
                .map(beverage -> (Bottle) beverage)
                .filter(Bottle::isAlcoholic)
                .map(bottle -> new BeverageResponseDTO(bottle.getName(), bottle.getPrice()))
                .toList();
    }

    @Override
    public List<BottleResponseDTO> getAllBottles() {
        return bottlesToBottleResponseDTOList(beverageRepository.findAllBottles());
    }

    @Override
    public List<CrateResponseDTO> getAllCrates() {
        return cratesToCrateResponseDTOList(beverageRepository.findAllCrates());
    }


}
