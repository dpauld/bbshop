package group7.repository;

import group7.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(value = "Order.userAndItems", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Order> findById(Long id);

}
