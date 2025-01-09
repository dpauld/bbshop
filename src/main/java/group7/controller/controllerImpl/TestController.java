package group7.controller.controllerImpl;

import group7.dto.BeverageResponseDto;
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
    public ResponseEntity<PaginatedResponseDto<BeverageResponseDto>> getAllBeveragesPaginated(Model model,
                                                                                   @RequestParam(value="page", required = false, defaultValue = "#{paginationProperties.pageNumber}") Integer pageNumber,
                                                                                   @RequestParam(value="size", required = false, defaultValue = "#{paginationProperties.beveragePageSize}") Integer pageSize,
                                                                                   @RequestParam(value="sort", required = false, defaultValue = "#{paginationProperties.sortBy}") String sortBy,
                                                                                   @RequestParam(value="direction", required = false, defaultValue = "#{paginationProperties.sortDir}") String sortDir){
        PaginatedResponseDto<BeverageResponseDto> paginatedResponseDto = this.beverageService.getAllBeveragesPaginated(pageNumber, pageSize, sortBy, sortDir);
        model.addAttribute("beverages", paginatedResponseDto.getContent());
        model.addAttribute("currentPage", paginatedResponseDto.getPageNumber());
        model.addAttribute("pageSize", paginatedResponseDto.getPageSize());
        model.addAttribute("totalPages", paginatedResponseDto.getTotalPages());
        model.addAttribute("isLastPage", paginatedResponseDto.isLastPage());
        // model.addAttribute("movie", new Movie());
        return new ResponseEntity<>(paginatedResponseDto,HttpStatus.OK);
    }

//    @GetMapping(value = "/bevjson", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PaginatedResponseDto<Beverage>> getAllBeveragesPaginated2(Model model){
//        PaginatedResponseDto<Beverage> paginatedResponseDto = this.beverageService.getAllBeveragesPaginated(1, 10, "Id", "ASC");
//        model.addAttribute("beverages", paginatedResponseDto.getContent());
//        model.addAttribute("currentPage", paginatedResponseDto.getPageNumber());
//        model.addAttribute("pageSize", paginatedResponseDto.getPageSize());
//        model.addAttribute("totalPages", paginatedResponseDto.getTotalPages());
//        model.addAttribute("isLastPage", paginatedResponseDto.isLastPage());
//        // model.addAttribute("movie", new Movie());
//        return new ResponseEntity<>(paginatedResponseDto,HttpStatus.OK);
//    }
}
