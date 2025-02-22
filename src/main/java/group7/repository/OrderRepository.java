package group7.repository;

import group7.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(value = "Order.withItemsAndBeverage", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Order> findById(Long id);

    @Override
    @EntityGraph(value="Order.withItemsAndBeverage", type = EntityGraph.EntityGraphType.LOAD) // entity graph solution
    Page<Order> findAll(Pageable pageable);

    @Override
    @EntityGraph(value="Order.withItemsAndBeverage", type = EntityGraph.EntityGraphType.LOAD) // entity graph solution
    List<Order> findAll();

    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.user.id = :userId ORDER BY o.user.id ASC")
    @EntityGraph(value = "Order.withItemsAndBeverage", type = EntityGraph.EntityGraphType.LOAD)
    List<Order> findOrdersByUserIdWithItems(Long userId);
}
