package group7.repository;

import group7.entity.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(value = "OrderItem.beverage", type = EntityGraph.EntityGraphType.LOAD)
    Optional<OrderItem> findById(Long id);
}
