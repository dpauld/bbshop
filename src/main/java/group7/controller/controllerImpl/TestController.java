package group7.controller.controllerImpl;

import group7.dto.PaginatedResponseDto;
import group7.entity.Beverage;
import group7.properties.PaginationProperties;
//import group7.service2.BasketService;
import group7.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController
@Controller
public class TestController {
    private final BeverageService beverageService;
    //private final BasketService basketService;
    private final PaginationProperties paginationProperties;

//    @Autowired
//    public TestController(BeverageService beverageService, BasketService basketService, PaginationProperties paginationProperties) {
//        this.beverageService = beverageService;
//        this.basketService = basketService;
//        this.paginationProperties = paginationProperties;
//    }

    @Autowired
    public TestController(BeverageService beverageService, PaginationProperties paginationProperties) {
        this.beverageService = beverageService;
        this.paginationProperties = paginationProperties;
    }
    @GetMapping(value = "/beveragesJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginatedResponseDto<Beverage>> getAllBeveragesPaginated(Model model,
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
        return new ResponseEntity<>(paginatedResponseDto,HttpStatus.OK);
    }
}
