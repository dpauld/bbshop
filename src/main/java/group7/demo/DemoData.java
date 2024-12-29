package group7.demo;

import group7.dto.BeverageResponseDTO;
import group7.entity.Bottle;
import group7.entity.Crate;
import group7.repository.BeverageRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoData {
    private BeverageRepository beverageRepository;
    private static final String COCA_COLA_IMAGE = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIxheN74qXKhIgyRTVf_w67JIX4bTmzSvEFQ&s";
    private static final String CRATE_IMAGE = "https://image.invaluable.com/housePhotos/abell/07/764507/H0068-L364202388.JPG";

    @Autowired
    public DemoData(BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    /**
     * We create some demo data here, when the database schema is created and the app is ready.
     *
     * @param event
     */
    @EventListener
    public void createDemoData(ApplicationReadyEvent event) {
        createAndSaveDemoBeverages();
    }
    // Demo Data Management
//    public List<BeverageResponseDTO> getDemoBeverages() {
//        if (beverageRepository.count() == 0) {
//            createAndSaveDemoBeverages();
//        }
//        return getAllBeverages();
//    }

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
        bottle.setName("CocaCola123");
        bottle.setInStock(100);
        bottle.setSupplier("Coca Cola Company");
        return bottle;
    }

    private List<Crate> createDemoCrates(Bottle bottle) {
        Crate crate1 = createSingleDemoCrate(bottle);
        Crate crate2 = createSingleDemoCrate(bottle);
        return List.of(crate1, crate2);
    }

    private Crate createSingleDemoCrate(Bottle bottle) {
        Crate crate = new Crate();
        crate.setName("CocaColaCrate123");
        crate.setCratePic(CRATE_IMAGE);
        crate.setCratesInStock(10);
        crate.setNoOfBottles(10);
        crate.setPrice(5.0);
        crate.setBottle(bottle);
        return crate;
    }

}
