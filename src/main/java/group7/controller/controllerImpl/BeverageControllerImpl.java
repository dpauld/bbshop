package group7.controller.controllerImpl;

import group7.configuration.customClasses.CustomModelMapper;
import group7.controller.BeverageController;
import group7.dto.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/beverages")
public class BeverageControllerImpl implements BeverageController {

    private final BeverageService beverageService;
    private final PaginationProperties paginationProperties;
    private final CustomModelMapper modelMapper;

    @Autowired
    public BeverageControllerImpl(BeverageService beverageService, PaginationProperties paginationProperties, CustomModelMapper modelMapper) {
        this.beverageService = beverageService;
        this.paginationProperties = paginationProperties;
        this.modelMapper = modelMapper;
    }

//    @GetMapping("/beverages/bottles")
//    public String getAllBotlles(Model model) {
//        model.addAttribute("bottles", beverageService.getAllBottles());
//        return "beverages";
//    }

    /**
     * Displays beverages with pagination
     *
     * Url might look like /beverages?page=0&size=10&sort=name&direction=asc
     *
     */
    @Override
    @GetMapping("")
    public String getAllBeveragesPaginated(Model model,
                                           @RequestParam(value="page", required = false, defaultValue = "#{paginationProperties.pageNumber}") Integer pageNumber,
                                           @RequestParam(value="size", required = false, defaultValue = "#{paginationProperties.beveragePageSize}") Integer pageSize,
                                           @RequestParam(value="sort", required = false, defaultValue = "#{paginationProperties.sortBy}") String sortBy,
                                           @RequestParam(value="direction", required = false, defaultValue = "#{paginationProperties.sortDir}") String sortDir){
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        PaginatedResponseDto<BeverageResponseDto> paginatedResponseDto = this.beverageService.getAllBeveragesPaginated(pageNumber-1, pageSize, sortBy, sortDir);
        model.addAttribute("beverages", paginatedResponseDto.getContent());
        model.addAttribute("currentPage", paginatedResponseDto.getPageNumber()+1);
        model.addAttribute("pageSize", paginatedResponseDto.getPageSize());
        model.addAttribute("sortBy",sortBy);
        model.addAttribute("totalPages", paginatedResponseDto.getTotalPages());
        model.addAttribute("isLastPage", paginatedResponseDto.isLastPage());
       // model.addAttribute("movie", new Movie());
        return "bevereges";

    }

    @Override
    @GetMapping("/{id}")
    public String getBeverageById(@PathVariable Long id, HttpSession session, Model model) {
        //log.info("Received a id for deletion: " + id);
        BeverageResponseDto beverage = beverageService.findBeverageById(id);
        model.addAttribute("beverage", beverage);
        return "beverage";
    }

    @Override
    @GetMapping(value = "/crates/json", produces = MediaType.APPLICATION_JSON_VALUE)
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
//        return "beveragesCrates";
//    }

    /***** From here on most of the codes are related to admin accessible create, update, delete *****/

     //Test code to view data, intial plan was to use single method to update any beverage
//    @ResponseBody
//    @GetMapping(value = "beverages/update/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<BeverageResponseDto> showUpdateForm(@PathVariable("id") Long beverageId, Model model) {
//        BeverageResponseDto beverageResponseDto = beverageService.findBeverageById(beverageId);
//        BeverageCreateDto beverageCreateDto = modelMapper.map(beverageResponseDto, BeverageCreateDto.class);
//        model.addAttribute("beverage", beverageCreateDto);
//        return new ResponseEntity<>(beverageResponseDto, HttpStatus.OK);
//    }

    //this is used to fetch data to populate the crate form to select a bottle
    @Override
    @GetMapping(value = "/bottles/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Bottle>>  getAllBotllesJson(Model model) {
        //model.addAttribute("bottles", beverageService.getAllBottles());
        List<Bottle> bottles = beverageService.getAllBottles();
        if (bottles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bottles, HttpStatus.OK);
    }

    @PostMapping("/bottles")
    @Override
    public String createBottle(@Validated(BottleGroup.class) BeverageCreateDto beverageCreateDto, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        log.info("POSTed a new Bottle: " + beverageCreateDto);
        if (errors.hasErrors()) {
            log.info("Bottle creation contained errors: " + beverageCreateDto.toString());
            model.addAttribute("beverageCreateDto", beverageCreateDto);
            model.addAttribute("beverages", beverageService.getAllBeveragesWithDetails());
            return "bevManagement";
        }
        beverageService.createBottle(beverageCreateDto);
        redirectAttributes.addFlashAttribute("success", "Beverage created successfully!");
        return "redirect:/admin/beverages";
    }

    @PostMapping("/crates")
    @Override
    public String createCrate(@Validated(CrateGroup.class) @ModelAttribute("beverageCreateDto") BeverageCreateDto beverageCreateDto, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        log.info("POSTed a new crate: " + beverageCreateDto);
        if (errors.hasErrors()) {
            log.info("Crate creation contained errors: " + beverageCreateDto.toString());
            model.addAttribute("beverageCreateDto", beverageCreateDto);
            model.addAttribute("beverages", beverageService.getAllBeveragesWithDetails());
            return "bevManagement";
        }
        beverageService.createCrate(beverageCreateDto);
        redirectAttributes.addFlashAttribute("success", "Beverage created successfully!");
        return "redirect:/admin/beverages";
    }

    @DeleteMapping("/{id}")
    @Override
    public String deleteBeverage(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        log.info("Received a id for deletion: " + id);
        beverageService.deleteBeverage(id);
        redirectAttributes.addFlashAttribute("success", "Beverage deleted successfully!");
        return "redirect:/admin/beverages";
    }

    @GetMapping("/update/{id}")
    @Override
    public String showUpdateForm(@PathVariable("id") Long beverageId, Model model) {
        BeverageResponseDto beverageResponseDto = beverageService.findBeverageById(beverageId);
        BeverageCreateDto beverageCreateDto = modelMapper.map(beverageResponseDto, BeverageCreateDto.class);
        model.addAttribute("beverage", beverageCreateDto);
        return "beverageUpdateForm";
    }

    @PutMapping("/bottles/{id}")
    @Override
    public String updateBottle(@PathVariable Long id,
                               @Validated(BottleGroup.class) @ModelAttribute("beverage") BeverageCreateDto beverageCreateDto,
                               Model model, RedirectAttributes redirectAttributes) {
        log.info("Received a id for update: " + id);
        BeverageResponseDto updatedBeverageResponseDto = beverageService.updateBeverage(beverageCreateDto, id);
        log.info("Bottle updated: " + updatedBeverageResponseDto.toString());
        redirectAttributes.addFlashAttribute("success", "Beverage updated successfully!");
        return "redirect:/admin/beverages";
    }

    @PutMapping("/crates/{id}")
    @Override
    public String updateCrate(@PathVariable Long id,
                              @Validated(CrateGroup.class)  @ModelAttribute("beverage") BeverageCreateDto beverageCreateDto,
                              Model model, RedirectAttributes redirectAttributes) {
        log.info("Received a id for update: " + id);
        BeverageResponseDto updatedBeverageResponseDto = beverageService.updateBeverage(beverageCreateDto, id);
        log.info("Crate updated: " + updatedBeverageResponseDto.toString());
        redirectAttributes.addFlashAttribute("success", "Beverage updated successfully!");
        return "redirect:/admin/beverages";
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
