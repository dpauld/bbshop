package group7.repositories;

import group7.entities.BeverageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BeverageRepository extends JpaRepository<BeverageEntity, Long> {

    @Query("SELECT b FROM BeverageEntity b WHERE TYPE(b) = BottleEntity")
    List<BeverageEntity> findAllBottles();

    @Query("SELECT b FROM BeverageEntity b WHERE TYPE(b) = CrateEntity")
    List<BeverageEntity> findAllCrates();
}
