package group7.repository;

import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    // This repository handles both Bottle and Crate entities at the same time

    @Query("SELECT b FROM Beverage b WHERE TYPE(b) = Bottle")
    List<Bottle> findAllBottles();

    @Query("SELECT b FROM Beverage b WHERE TYPE(b) = Crate")
    List<Crate> findAllCrates();
}
