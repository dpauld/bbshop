package group7.controller.controllerImpl;

import group7.configuration.customClasses.CustomModelMapper;
import group7.controller.BeverageController;
import group7.dto.*;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import group7.properties.PaginationProperties;
//import group7.service.BasketService;
import group7.service.BeverageService;
import group7.validation.group.BottleGroup;
import group7.validation.group.CrateGroup;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class BeverageControllerImpl implements BeverageController {

    private final BeverageService beverageService;
    //private final BasketService basketService;
    private final PaginationProperties paginationProperties;
    private final CustomModelMapper modelMapper;

    //    @Autowired
//    public BeverageControllerImpl(BeverageService beverageService, BasketService basketService, PaginationProperties paginationProperties) {
//        this.beverageService = beverageService;
//        this.basketService = basketService;
//        this.paginationProperties = paginationProperties;
//    }
    @Autowired
    public BeverageControllerImpl(BeverageService beverageService, PaginationProperties paginationProperties, CustomModelMapper modelMapper) {
        this.beverageService = beverageService;
        this.paginationProperties = paginationProperties;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/beverages")
    public String getAllBeverages(Model model) {
        //List<Beverage> beverages = beverageService.getAllBeverages();
        //model.addAttribute("beverages", beverageService.getAllBeverages());
        model.addAttribute("beverageCreateDto", new BeverageCreateDto());
        //model.addAttribute("bottleCreateDto", new BottleCreateDto());
        //model.addAttribute("crateCreateDto", new CrateCreateDto());
//        model.addAttribute("bottles", beverageService.getAllBottles());
//        model.addAttribute("crates", beverageService.getAllCrates());
//        return "test";

        List<BeverageResponseDto> beverages = beverageService.getAllBeveragesWithDetails();

        model.addAttribute("beverages", beverages);
        return "beverages";
    }

    @Override
    public String addBeverageToBasket(Long id, HttpSession session) {
        return "";
    }


//    @GetMapping("/beverages/bottles")
//    public String getAllBotlles(Model model) {
//        model.addAttribute("bottles", beverageService.getAllBottles());
//        return "beverages";
//    }

    @GetMapping(value = "/beverages/bottles/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Bottle>>  getAllBotllesJson(Model model) {
        //model.addAttribute("bottles", beverageService.getAllBottles());
        List<Bottle> bottles = beverageService.getAllBottles();
        if (bottles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bottles, HttpStatus.OK);
    }

    @GetMapping(value = "/beverages/crates/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Crate>> getAllCratesJson(Model model) {
        //model.addAttribute("crates", beverageService.getAllCrates());
        List<Crate> crates = beverageService.getAllCrates();
        if (crates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(crates, HttpStatus.OK);
    }

//    @GetMapping("/beverages/crates")
//    public String getAllCrates(Model model) {
//        model.addAttribute("crates", beverageService.getAllCrates());
//        return "beverages";
//    }

    /**
     * Displays beverages with pagination
     *
     * Url might look like /beverages?page=0&size=10&sort=name&direction=asc
     *
     */
    @GetMapping("/beverages2")
    public String getAllBeveragesPaginated(Model model,
        @RequestParam(value="page", required = false) Integer pageNumber,
        @RequestParam(value="size", required = false) Integer pageSize,
        @RequestParam(value="sort", required = false) String sortBy,
        @RequestParam(value="direction", required = false) String sortDir){
        // Use default values from the PaginationProperties bean if parameters are null
        pageNumber = (pageNumber != null) ? pageNumber : paginationProperties.getPageNumber();
        pageSize = (pageSize != null) ? pageSize : paginationProperties.getBeveragePageSize();
        sortBy = (sortBy != null) ? sortBy : paginationProperties.getSortBy();
        sortDir = (sortDir != null) ? sortDir : paginationProperties.getSortDir();

        PaginatedResponseDto<Beverage> paginatedResponseDto = this.beverageService.getAllBeveragesPaginated(pageNumber, pageSize, sortBy, sortDir);
        model.addAttribute("beverages", paginatedResponseDto.getContent());
        model.addAttribute("currentPage", paginatedResponseDto.getPageNumber());
        model.addAttribute("pageSize", paginatedResponseDto.getPageSize());
        model.addAttribute("totalPages", paginatedResponseDto.getTotalPages());
        model.addAttribute("isLastPage", paginatedResponseDto.isLastPage());
       // model.addAttribute("movie", new Movie());
        return "beverages2";
    }

    @PostMapping("/beverages/bottles")
    public String createBottle(@Validated(BottleGroup.class) BeverageCreateDto beverageCreateDto, Errors errors, Model model) {
        log.info("POSTed a new Bottle: " + beverageCreateDto);
        if (errors.hasErrors()) {
            log.info("Bottle creation contained errors: " + beverageCreateDto.toString());
            model.addAttribute("beverageCreateDto", beverageCreateDto);
            model.addAttribute("beverages", beverageService.getAllBeveragesWithDetails());
            return "beverages";
        }
        beverageService.createBottle(beverageCreateDto);
        return "redirect:/beverages";
    }

    @PostMapping("/beverages/crates")
    public String createCrate(@Validated(CrateGroup.class) @ModelAttribute("beverageCreateDto") BeverageCreateDto beverageCreateDto, Errors errors, Model model) {
        log.info("POSTed a new crate: " + beverageCreateDto);
        if (errors.hasErrors()) {
            log.info("Crate creation contained errors: " + beverageCreateDto.toString());
            model.addAttribute("beverageCreateDto", beverageCreateDto);
            model.addAttribute("beverages", beverageService.getAllBeveragesWithDetails());
            return "beverages";
        }
        beverageService.createCrate(beverageCreateDto);
        return "beverages";
    }

    @DeleteMapping("/beverages/{id}")
    public String deleteBeverage(@PathVariable Long id, HttpSession session) {
        log.info("Received a id for deletion: " + id);
        beverageService.deleteBeverage(id);
        return "redirect:/beverages";
    }

    @GetMapping("/beverages/{id}")
    public String getBeverageById(@PathVariable Long id, HttpSession session, Model model) {
        //log.info("Received a id for deletion: " + id);
        BeverageResponseDto beverage = beverageService.findBeverageById(id);
        model.addAttribute("beverage", beverage);
        return "beverage";
    }

    //Test code to view data
//    @ResponseBody
//    @GetMapping(value = "beverages/update/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<BeverageResponseDto> showUpdateForm(@PathVariable("id") Long beverageId, Model model) {
//        BeverageResponseDto beverageResponseDto = beverageService.findBeverageById(beverageId);
//        BeverageCreateDto beverageCreateDto = modelMapper.map(beverageResponseDto, BeverageCreateDto.class);
//        model.addAttribute("beverage", beverageCreateDto);
//        return new ResponseEntity<>(beverageResponseDto, HttpStatus.OK);
//    }

    @GetMapping("beverages/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long beverageId, Model model) {
        BeverageResponseDto beverageResponseDto = beverageService.findBeverageById(beverageId);
        BeverageCreateDto beverageCreateDto = modelMapper.map(beverageResponseDto, BeverageCreateDto.class);
        model.addAttribute("beverage", beverageCreateDto);
        return "beverageUpdateForm";
    }

    @PutMapping("/beverages/bottles/{id}")
    public String updateBottle(@PathVariable Long id,
                               @Validated(BottleGroup.class) @ModelAttribute("beverage") BeverageCreateDto beverageCreateDto,
                               Model model) {
        log.info("Received a id for update: " + id);
        BeverageResponseDto updatedBeverageResponseDto = beverageService.updateBeverage(beverageCreateDto, id);
        log.info("Bottle updated: " + updatedBeverageResponseDto.toString());
        return "redirect:/beverages";
    }

    @PutMapping("/beverages/crates/{id}")
    public String updateCrate(@PathVariable Long id,
                              @Validated(CrateGroup.class)  @ModelAttribute("beverage") BeverageCreateDto beverageCreateDto,
                              Model model) {
        log.info("Received a id for update: " + id);
        BeverageResponseDto updatedBeverageResponseDto = beverageService.updateBeverage(beverageCreateDto, id);
        log.info("Crate updated: " + updatedBeverageResponseDto.toString());
        return "redirect:/beverages";
    }
//
//    @PostMapping("/bottles")
//    public String addBottle(@Valid @ModelAttribute("bottle") BottleDto bottleDto, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("bottleFields", true);
//            model.addAttribute("crateFields", false);
//            return "beverageForm"; // Return to the form view with error messages
//        }
//        beverageService.saveBottle(bottleDto);
//        return "redirect:/beverages"; // Redirect to the list of beverages
//    }

    //    @PostMapping("/admin/addCrate")
//    public String createNewCrate(@Valid @ModelAttribute AddCrateRequestDTO addCrateRequestDTO, Errors errors, Model model) {
//        if (errors.hasErrors()) {
//            return getAllBeverageEditUI(model);
//        }
//        beverageService.addCrate(addCrateRequestDTO);
//        return "redirect:/admin";
//    }
//        @PostMapping("/crates")
//        public String addCrate(@Valid @ModelAttribute("crate") CrateDto crateDto, BindingResult result, Model model) {
//            if (result.hasErrors()) {
//                model.addAttribute("bottleFields", false);
//                model.addAttribute("crateFields", true);
//                model.addAttribute("bottles", beverageService.getAllBottles());
//                return "beverageForm"; // Return to the form view with error messages
//            }
//            beverageService.saveCrate(crateDto);
//            return "redirect:/beverages"; // Redirect to the list of beverages
//        }
}
