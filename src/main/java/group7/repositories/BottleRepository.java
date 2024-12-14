package group7.repositories;

import group7.entities.BottleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BottleRepository extends JpaRepository<BottleEntity, Long> {
}
