package group7.service.serviceImpl;

import group7.dto.*;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.repository.BeverageRepository;
import group7.service.BeverageService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import group7.entity.Crate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BeverageServiceImpl implements BeverageService {

    private final ModelMapper modelMapper;
    private final BeverageRepository beverageRepository;

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
    @Transactional
    @Override
    public BeverageResponseDTO addBeverage(AddBeverageRequestDTO addBeverageRequestDTO) {
        Beverage beverage = beverageRequestDTOToBeverage(addBeverageRequestDTO);
        Beverage savedBeverage = beverageRepository.save(beverage);
        return beverageToBeverageResponseDTO(savedBeverage);
    }

    @Transactional
    @Override
    public BeverageResponseDTO addCrate(AddCrateRequestDTO addCrateRequestDTO) {
        Crate crate = modelMapper.map(addCrateRequestDTO, Crate.class);
        Crate savedCrate = beverageRepository.save(crate);  // FIXME org.springframework.orm.ObjectOptimisticLockingFailureException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect): [group7.entity.Crate#4]
        return modelMapper.map(savedCrate, BeverageResponseDTO.class);
    }

    @Transactional
    @Override
    public BeverageResponseDTO addBottle(AddBottleRequestDTO addBottleRequestDTO) {
        Bottle bottle = modelMapper.map(addBottleRequestDTO, Bottle.class);
        Bottle savedBottle = beverageRepository.save(bottle);
        return modelMapper.map(savedBottle, BeverageResponseDTO.class);
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
                .map(bottle -> (BeverageResponseDTO) createBottleResponseDTO(bottle))
                .toList();
    }

    @Override
    public List<CrateResponseDTO> getAllCrates() {
        return beverageRepository.findAllCrates().stream()
                .map(this::createCrateResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BottleResponseDTO> getAllBottles() {
        return beverageRepository.findAllBottles().stream()
                .map(this::createBottleResponseDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<BeverageResponseDTO> getSoldBeverages() {
        // Implementation for sold beverages if needed
        return List.of();
    }

    private BottleResponseDTO createBottleResponseDTO(Bottle bottle) {
        BottleResponseDTO bottleResponseDTO = new BottleResponseDTO(bottle.getId(), bottle.getBottlePic(), bottle.getVolume(), bottle.isAlcoholic(), bottle.getVolumePercent(), bottle.getSupplier(), bottle.getInStock());
        bottleResponseDTO.setName(bottle.getName());
        bottleResponseDTO.setPrice(bottle.getPrice());
        return bottleResponseDTO;
    }

    private CrateResponseDTO createCrateResponseDTO(Crate crate) {
        CrateResponseDTO crateResponseDTO = new CrateResponseDTO(crate.getId(), crate.getCratePic(), crate.getCratesInStock(), crate.getNoOfBottles(), modelMapper.map(crate.getBottle(), BottleResponseDTO.class));
        crateResponseDTO.setName(crate.getName());
        crateResponseDTO.setPrice(crate.getPrice());
        return crateResponseDTO;
    }
}
