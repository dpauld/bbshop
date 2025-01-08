package group7.users;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findUserByUsername(String username) throws UsernameNotFoundException;

    @EntityGraph(value = "User.withOrdersAndAddresses", type = EntityGraph.EntityGraphType.LOAD)
    User findByUsername(String username) throws UsernameNotFoundException;

    @EntityGraph(value = "User.withOrdersAndAddresses", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(Long id);

    @EntityGraph(value = "User.withOrdersAndAddresses", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.id = :userId")
    User findByIdWithOrders(Long userId);

}
