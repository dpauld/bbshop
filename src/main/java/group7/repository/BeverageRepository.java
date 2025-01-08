package group7.repository;

import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    // This repository handles both Bottle and Crate entities at the same time
    @Query("SELECT b FROM Beverage b WHERE TYPE(b) = Bottle")
    List<Bottle> findAllBottles();

    @Query("SELECT b FROM Beverage b WHERE TYPE(b) = Crate")
    List<Crate> findAllCrates();

    /*Find By Id*/
    @Query("SELECT b FROM Beverage b WHERE TYPE(b) = Bottle AND b.id = :id")
    Optional<Bottle> findBottleById(Long id);

    @Query("SELECT c FROM Beverage c WHERE TYPE(c) = Crate AND c.id = :id")
    Optional<Crate> findCrateById(Long id);

    //beverage object as return type, same as findByID
    @Query("SELECT b FROM Beverage b WHERE b.id = :id")
    Optional<Beverage> findBeverageById(Long id);
}
