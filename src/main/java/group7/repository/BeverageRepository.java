package group7.repository;

import group7.entity.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    // This repository handles both Bottle and Crate entities at the same time

    @Query("SELECT b FROM Beverage b WHERE TYPE(b) = Bottle")
    List<Beverage> findAllBottles();

    @Query("SELECT b FROM Beverage b WHERE TYPE(b) = Crate")
    List<Beverage> findAllCrates();
}
