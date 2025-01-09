package group7.service.serviceImpl;

import group7.component.Basket;
import group7.configuration.customClasses.CustomModelMapper;
import group7.dto.*;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import group7.exception.InsufficientStockException;
import group7.exception.ResourceNotFoundException;
import group7.repository.BeverageRepository;
import group7.service.BasketService;
import group7.service.BeverageService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BeverageServiceImpl implements BeverageService {

    private final CustomModelMapper modelMapper;
    private final BeverageRepository beverageRepository;
    private final BasketService basketService;

    @Autowired
    public BeverageServiceImpl(CustomModelMapper modelMapper, BeverageRepository beverageRepository, BasketService basketService) {
        this.modelMapper = modelMapper;
        this.beverageRepository = beverageRepository;
        this.basketService = basketService;
    }

    // CRUD Operations
    @Transactional
    public Bottle createBottle(String name, double price, int inStock, String picture,
                               double volume, boolean isAlcoholic, double volumePercent, String supplier) {
        Bottle bottle = new Bottle(name, price, inStock, picture, volume, isAlcoholic, volumePercent, supplier);
        return beverageRepository.save(bottle);
    }

    public Crate createCrate(String name, double price, int inStock, String picture, int noOfBottles, Bottle bottle) {
        Crate crate = new Crate(name, price, inStock, picture, noOfBottles, bottle);
        return beverageRepository.save(crate);
    }

    @Override
    public List<Beverage> getAllBeverages() {
       return beverageRepository.findAll();
    }

    public List<BeverageResponseDto> getAllBeveragesWithDetails() {
        List<Beverage> beverages = beverageRepository.findAll();
        List<BeverageResponseDto> beverageResponseDtos = new ArrayList<>();
        for (Beverage beverage : beverages) {
            BeverageResponseDto dto = modelMapper.map(beverage, BeverageResponseDto.class);

            if (beverage instanceof Bottle bottle) {
                dto.setType("Bottle");
                dto.setVolume(bottle.getVolume());
                dto.setAlcoholic(bottle.isAlcoholic());
                dto.setVolumePercent(bottle.getVolumePercent());
                dto.setSupplier(bottle.getSupplier());
            } else if (beverage instanceof Crate crate) {
                dto.setType("Crate");
                dto.setVolume(crate.getBottle().getVolume());
                dto.setAlcoholic(crate.getBottle().isAlcoholic());
                dto.setVolumePercent(crate.getBottle().getVolumePercent());
                dto.setSupplier(crate.getBottle().getSupplier());
                dto.setNoOfBottles(crate.getNoOfBottles());
            }
            beverageResponseDtos.add(dto);
        }
        return beverageResponseDtos;
    }

    //pagination
    @Override
    public PaginatedResponseDto<Beverage> getAllBeveragesPaginatedOld(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        //if desc passed as value then sort by descending order, otherwise sort by ascending
        Sort sortObj = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        Pageable pageable = null;
        try {
            pageable = PageRequest.of(pageNumber, pageSize, sortObj);
        } catch (Exception e) {
            // This catches IllegalArgument exception when parameter value passed as negative and zeros(in some cases) pageSize=-1 or pageNumber=-10 or sortBy="randomText"
            throw new ResourceNotFoundException("Oops, something went wrong. Please try again later.");
        }

        Page<Beverage> pageBeverages = beverageRepository.findAll(pageable);

        // Adjust pageNumber if out of range
        if (pageNumber >= pageBeverages.getTotalPages() && pageBeverages.getTotalPages() > 0) {
            pageable = PageRequest.of(pageBeverages.getTotalPages() - 1, pageSize, sortObj);
            pageBeverages = beverageRepository.findAll(pageable);
        }

        //get the post chunk
        List<Beverage> beverages = pageBeverages.getContent();
        log.info(beverages.toString());

        //create PaginatedResponseDto and set its properties
        PaginatedResponseDto<Beverage> beveragePaginatedResponseDto = new PaginatedResponseDto<>();
        beveragePaginatedResponseDto.setContent(beverages);//provide dto if there is any
        beveragePaginatedResponseDto.setPageNumber(pageNumber);
        beveragePaginatedResponseDto.setPageNumber(pageNumber);
        beveragePaginatedResponseDto.setPageSize(pageSize);
        beveragePaginatedResponseDto.setTotalElements(pageBeverages.getTotalElements());
        beveragePaginatedResponseDto.setTotalPages(pageBeverages.getTotalPages());
        beveragePaginatedResponseDto.setLastPage(pageBeverages.isLast());
        return beveragePaginatedResponseDto;
    }

    @Override
    public PaginatedResponseDto<BeverageResponseDto> getAllBeveragesPaginated(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        //if desc passed as value then sort by descending order, otherwise sort by ascending
        Sort sortObj = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        Pageable pageable = null;
        try {
            pageable = PageRequest.of(pageNumber, pageSize, sortObj);
        } catch (Exception e) {
            // This catches IllegalArgument exception when parameter value passed as negative and zeros(in some cases) pageSize=-1 or pageNumber=-10 or sortBy="randomText"
            throw new ResourceNotFoundException("Oops, something went wrong. Please try again later.");
        }

        Page<Beverage> pageBeverages = beverageRepository.findAll(pageable);

        // Adjust pageNumber if out of range
        if (pageNumber >= pageBeverages.getTotalPages() && pageBeverages.getTotalPages() > 0) {
            pageable = PageRequest.of(pageBeverages.getTotalPages() - 1, pageSize, sortObj);
            pageBeverages = beverageRepository.findAll(pageable);
        }

        //get the post chunk
        List<Beverage> beverages = pageBeverages.getContent();
        log.info(beverages.toString());

        //create PaginatedResponseDto and set its properties
        // PaginatedResponseDto<Beverage> beveragePaginatedResponseDto = new PaginatedResponseDto<>();
        //beveragePaginatedResponseDto.setContent(beverages);//provide dto if there is any

        PaginatedResponseDto<BeverageResponseDto> beveragePaginatedResponseDto = new PaginatedResponseDto<>();
        List<BeverageResponseDto> beverageRespDtos = modelMapper.mapList(beverages, BeverageResponseDto.class);
        // Assign type based on noOfBottles using ternary operator
        beverageRespDtos.forEach(dto -> dto.setType(dto.getNoOfBottles() > 0 ? "Crate" : "Bottle"));

        beveragePaginatedResponseDto.setContent(beverageRespDtos);
        beveragePaginatedResponseDto.setPageNumber(pageNumber);
        beveragePaginatedResponseDto.setPageNumber(pageNumber);
        beveragePaginatedResponseDto.setPageSize(pageSize);
        beveragePaginatedResponseDto.setTotalElements(pageBeverages.getTotalElements());
        beveragePaginatedResponseDto.setTotalPages(pageBeverages.getTotalPages());
        beveragePaginatedResponseDto.setLastPage(pageBeverages.isLast());
        return beveragePaginatedResponseDto;
    }

    @Override
    public BeverageResponseDto findBeverageById(Long id) {
        Beverage beverage = beverageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Beverage not found with id: " + id));
        BeverageResponseDto beverageResponseDto = modelMapper.map(beverage, BeverageResponseDto.class);
        if(beverage instanceof Bottle bottle) {
            beverageResponseDto.setType("Bottle");
        }else if (beverage instanceof Crate crate) {
            beverageResponseDto.setType("Crate");
        }
        return beverageResponseDto;
    }

    @Override
    public List<Crate> getAllCrates() {
        return beverageRepository.findAllCrates();
    }

    @Override
    public Void deleteBeverage(Long beverageId) throws ResourceNotFoundException{
        Beverage beverage = beverageRepository.findById(beverageId).orElseThrow(()->new ResourceNotFoundException("Beverage", "Id", beverageId));
        log.info("Deleting the beverage: " + beverage.toString());
        beverageRepository.delete(beverage);
        return null;
    }

    @Override
    public BeverageResponseDto updateBeverage(BeverageCreateDto beverageCreateDto, Long beverageId) throws ResourceNotFoundException{
        Beverage beverage = this.beverageRepository.findById(beverageId).orElseThrow(()->new ResourceNotFoundException("Beverage", "Id", beverageId));

        beverage.setName(beverageCreateDto.getName());
        beverage.setPrice(beverageCreateDto.getPrice());
        beverage.setInStock(beverageCreateDto.getInStock());
        beverage.setPicture(beverageCreateDto.getPicture());

        if(beverage instanceof Bottle bottle) {
            bottle.setVolume(beverageCreateDto.getVolume());
            bottle.setVolumePercent(beverageCreateDto.getVolumePercent());
            bottle.setSupplier(beverageCreateDto.getSupplier());
        }
        //beverage.setType(beverageDto.getType());
       if(beverage instanceof Crate crate) {
           Bottle bottle = beverageRepository.findBottleById(beverageCreateDto.getCratesBottleId()).orElseThrow(()-> new ResourceNotFoundException("Bottle","Id", beverageCreateDto.getCratesBottleId()));
           crate.setBottle(bottle);
           crate.setNoOfBottles(beverageCreateDto.getNoOfBottles());
       }
       Beverage savedBeverage = beverageRepository.save(beverage);

       return modelMapper.map(savedBeverage, BeverageResponseDto.class);
    }

    @Override
    public Beverage update(Beverage beverage) {
        return beverageRepository.save(beverage);
    }

    @Override
    public List<Bottle> getAllBottles() {
        return beverageRepository.findAllBottles();
    }

    // Specialized Query Methods
    @Override
    public List<Beverage> getAlcoholicBeverages() {
        return beverageRepository.findAllBottles().stream()
                .filter(Bottle::isAlcoholic)
                .map(bottle -> (Beverage) bottle)
                .toList();
    }

    @Override
    public Crate createCrate(BeverageCreateDto beverageCreateDto) throws ResourceNotFoundException {
        Bottle bottle = beverageRepository.findBottleById(beverageCreateDto.getCratesBottleId())
                .orElseThrow(() -> new ResourceNotFoundException("Bottle not found with id: " + beverageCreateDto.getCratesBottleId()));
        beverageCreateDto.setBottle(bottle);
        Crate crate = modelMapper.map(beverageCreateDto, Crate.class);
        Crate savedCrate = beverageRepository.save(crate);
        return modelMapper.map(crate, Crate.class);
    }

    @Override
    public Bottle createBottle(BeverageCreateDto beverageCreateDto) {
        System.out.println("In createBottle");
        Bottle bottle = modelMapper.map(beverageCreateDto, Bottle.class);
        Bottle savedBottle = beverageRepository.save(bottle);
        return modelMapper.map(savedBottle, Bottle.class);
    }

    @Override
    public Beverage createBeverage(BeverageCreateDto beverageCreateDto) {
        return null;
    }

    @Override
    public List<Beverage> getSoldBeverages() {
        // Implementation for sold beverages if needed
        return List.of();
    }

    public BeverageResponseDto findBottleById(Long id) {
        Beverage beverage = beverageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bottle", "Id", id));
        if (beverage instanceof Bottle bottle) {
            return modelMapper.map(bottle, BeverageResponseDto.class);
        } else {
            throw new ResourceNotFoundException("Bottle", "Id", id);
        }
    }

    public BeverageResponseDto findCrateById(Long id) {
        Beverage beverage = beverageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Crate", "Id", id));
        if (beverage instanceof Crate crate) {
            BeverageResponseDto beverageResponseDto = modelMapper.map(crate, BeverageResponseDto.class);
            beverageResponseDto.setType("crate");
            return beverageResponseDto;
        } else {
            throw new ResourceNotFoundException("Crate", "Id", id);
        }
    }

    @Transactional
    @Override
    public boolean updateStock() {
        Basket basket = basketService.getBasket();
        if(basket.getItems().isEmpty()) {
            return false;
        }
        //log.info(basket.toString());
        for (BasketItemDto basketItem : basket.getItems()) {
            Beverage beverage = beverageRepository.findById(basketItem.getBeverage().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Beverage","Id", basketItem.getBeverage().getId()));

            if (basketItem.getQuantity() > beverage.getInStock()) {
                throw new InsufficientStockException("Quantity exceeds available stock for beverage: " + beverage.getName());
            }

            beverage.setInStock(beverage.getInStock() - basketItem.getQuantity());
            Beverage upBeaverae = beverageRepository.save(beverage);
            //log.info(upBeaverae.toString());
        }
        return true;
    }

    @Override
    public Beverage getBeverageById(Long id) {
        return beverageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Beverage", "Id", id));
    }
}
