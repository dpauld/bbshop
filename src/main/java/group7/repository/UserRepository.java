package group7.repository;

import group7.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findUserByUsername(String username) throws UsernameNotFoundException;
    //public boolean existsByUsernameIgnoreCase(String username) throws UsernameNotFoundException;//for postgress not preferred, as postgress is case sensitive
    //public boolean existsByEmailIgnoreCase(String email) throws UsernameNotFoundException;//same reason
    @Query(value = "SELECT EXISTS (SELECT 1 FROM users WHERE LOWER(username) = LOWER(:username))", nativeQuery = true)
    boolean existsByUsernameIgnoreCase(String username);
    @Query(value = "SELECT EXISTS (SELECT 1 FROM users WHERE LOWER(email) = LOWER(:email))", nativeQuery = true)
    boolean existsByEmailIgnoreCase(String email);

    @EntityGraph(value = "User.withOrdersAndAddresses", type = EntityGraph.EntityGraphType.LOAD)
    User findByUsername(String username) throws UsernameNotFoundException;

    @EntityGraph(value = "User.withOrdersAndAddresses", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(Long id);

    @EntityGraph(value = "User.withOrdersAndAddresses", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.id = :userId")
    User findByIdWithOrders(Long userId);

}
