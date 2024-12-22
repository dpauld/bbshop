package group7.repository;

import group7.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @EntityGraph(attributePaths = {"orders", "billingAddresses", "deliveryAddresses"},type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(Long id);
}
