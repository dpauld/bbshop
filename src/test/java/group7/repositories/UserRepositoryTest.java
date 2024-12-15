package group7.repositories;

import group7.entities.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class UserRepositoryTest {

    private static final LocalDate SAMPLE_BIRTHDAY = LocalDate.of(1970, 1, 1);
    private List<User> invalidUsers;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        invalidUsers = new ArrayList<>();
    }

    @Test
    public void testSavingUsersWithEmptyValues() {
        invalidUsers.add(new User(null, "", "123", SAMPLE_BIRTHDAY, List.of(), List.of()));
        invalidUsers.add(new User(null, "John Doe", "", SAMPLE_BIRTHDAY, List.of(), List.of()));
        invalidUsers.add(new User(null, "", "", SAMPLE_BIRTHDAY, List.of(), List.of()));

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAll(invalidUsers));
    }

    @Test
    public void testSavingUsersWithNullValues() {
        invalidUsers.add(new User(null, null, "123", SAMPLE_BIRTHDAY, List.of(), List.of()));
        invalidUsers.add(new User(null, "John Doe", null, SAMPLE_BIRTHDAY, List.of(), List.of()));
        invalidUsers.add(new User(null, "John Doe", "123", null, List.of(), List.of()));
        invalidUsers.add(new User(null, null, null, null, List.of(), List.of()));

        assertThrows(Exception.class, () -> userRepository.saveAll(invalidUsers));
    }

    @Test
    public void testSavingUsersWithInvalidBirthday() {
        LocalDate veryOldDate = LocalDate.of(1899, 12, 31);
        LocalDate futureDate = LocalDate.of(2049, 1, 1);

        invalidUsers.add(new User(null, "John Doe", "123", veryOldDate, List.of(), List.of()));
        invalidUsers.add(new User(null, "John Doe", "123", futureDate, List.of(), List.of()));

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAll(invalidUsers));
    }

    @Test
    public void testSavingUsersWithEqualUsernames() {
        invalidUsers.add(new User(null, "John Doe", "123", SAMPLE_BIRTHDAY, List.of(), List.of()));
        invalidUsers.add(new User(null, "John Doe", "321", SAMPLE_BIRTHDAY, List.of(), List.of()));

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAll(invalidUsers));
    }
}
