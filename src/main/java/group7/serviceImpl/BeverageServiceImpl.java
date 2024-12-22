package group7.serviceImpl;

import group7.dto.*;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.repository.BeverageRepository;
import group7.service.BeverageService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BeverageServiceImpl implements BeverageService {

    private final ModelMapper modelMapper;
    private final BeverageRepository beverageRepository;
    private static final String COCA_COLA_IMAGE = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIxheN74qXKhIgyRTVf_w67JIX4bTmzSvEFQ&s";
    private static final String CRATE_IMAGE = "https://image.invaluable.com/housePhotos/abell/07/764507/H0068-L364202388.JPG";

    @Autowired
    public BeverageServiceImpl(ModelMapper modelMapper, BeverageRepository beverageRepository) {
        this.modelMapper = modelMapper;
        this.beverageRepository = beverageRepository;
    }

    // DTO Mapping Methods
    @Override
    public BeverageResponseDTO beverageToBeverageResponseDTO(Beverage beverage) {
        return modelMapper.map(beverage, BeverageResponseDTO.class);
    }

    @Override
    public Beverage beverageRequestDTOToBeverage(AddBeverageRequestDTO addBeverageRequestDTO) {
        return modelMapper.map(addBeverageRequestDTO, Beverage.class);
    }

    @Override
    public List<BeverageResponseDTO> beveragesToBeverageResponseDTOList(List<Beverage> beverages) {
        return beverages.stream()
                .map(this::beverageToBeverageResponseDTO)
                .collect(Collectors.toList());
    }

    // CRUD Operations
    @Override
    public BeverageResponseDTO addBeverage(AddBeverageRequestDTO addBeverageRequestDTO) {
        Beverage beverage = beverageRequestDTOToBeverage(addBeverageRequestDTO);
        Beverage savedBeverage = beverageRepository.save(beverage);
        return beverageToBeverageResponseDTO(savedBeverage);
    }

    @Override
    public List<BeverageResponseDTO> getAllBeverages() {
        return beveragesToBeverageResponseDTOList(beverageRepository.findAll());
    }

    @Override
    public BeverageResponseDTO findBeveragesById(Long id) {
        return beverageRepository.findById(id)
                .map(this::beverageToBeverageResponseDTO)
                .orElseThrow(() -> new RuntimeException("Beverage not found with id: " + id));
    }

    // Specialized Query Methods
    @Override
    public List<BeverageResponseDTO> getAlcoholicBeverages() {
        return beverageRepository.findAllBottles().stream()
                .filter(beverage -> beverage instanceof Bottle)
                .map(beverage -> (Bottle) beverage)
                .filter(Bottle::isAlcoholic)
                .map(bottle -> new BeverageResponseDTO(bottle.getName(), bottle.getPrice()))
                .toList();
    }

    @Override
    public List<BeverageResponseDTO> getAllCrates() {
        return beverageRepository.findAllCrates().stream()
                .map(this::createBeverageResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BeverageResponseDTO> getAllBottles() {
        return beverageRepository.findAllBottles().stream()
                .map(this::createBeverageResponseDTO)
                .collect(Collectors.toList());
    }

    // Demo Data Management
    @Override
    public List<BeverageResponseDTO> getDemoBeverages() {
        if (beverageRepository.count() == 0) {
            createAndSaveDemoBeverages();
        }
        return getAllBeverages();
    }

    @Override
    public List<BeverageResponseDTO> getSoldBeverages() {
        // Implementation for sold beverages if needed
        return List.of();
    }

    @Transactional
    public void createAndSaveDemoBeverages() {
        // Create and save bottle
        Bottle cocaCola = createDemoBottle();
        Bottle savedBottle = beverageRepository.save(cocaCola);

        // Create and save crates
        List<Crate> crates = createDemoCrates(savedBottle);
        beverageRepository.saveAll(crates);
    }

    // Helper Methods
    private Bottle createDemoBottle() {
        Bottle bottle = new Bottle();
        bottle.setBottlePic(COCA_COLA_IMAGE);
        bottle.setVolume(1.5);
        bottle.setPrice(2.5);
        bottle.setName("Coca Cola");
        bottle.setInStock(100);
        bottle.setSupplier("Coca Cola Company");
        return bottle;
    }

    private List<Crate> createDemoCrates(Bottle bottle) {
        Crate crate1 = createDemoCrate(bottle);
        Crate crate2 = createDemoCrate(bottle);
        return List.of(crate1, crate2);
    }

    private Crate createDemoCrate(Bottle bottle) {
        Crate crate = new Crate();
        crate.setCratePic(CRATE_IMAGE);
        crate.setNoOfBottles(10);
        crate.setPrice(5.0);
        crate.setBottle(bottle);
        return crate;
    }

    private BeverageResponseDTO createBeverageResponseDTO(Beverage beverage) {
        return new BeverageResponseDTO(beverage.getName(), beverage.getPrice());
    }
}