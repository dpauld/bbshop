package group7.repository;

import group7.entity.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import util.SampleManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class UserRepositoryUnitTest {

    private List<User> invalidUsers;
    private SampleManager<User> sampleManager;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        invalidUsers = new ArrayList<>();
        sampleManager = new SampleManager<>(invalidUsers);
    }

    @Test
    public void testSavingAndRetrievingValidUser() {
        User user = getSampleUser();
        User savedUser = userRepository.save(user);
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());

        assertThat(retrievedUser).isPresent();
    }

    @Test
    public void testSavingUsersWithEmptyValues() {
        sampleManager.addChangedSample(this::getSampleUser, user -> user.setUsername(""));
        sampleManager.addChangedSample(this::getSampleUser, user -> user.setPassword(""));
        sampleManager.addChangedSample(
                this::getSampleUser,
                user -> {
                    user.setUsername("");
                    user.setPassword("");
                }
        );

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAll(invalidUsers));
    }

    @Test
    public void testSavingUsersWithNullValues() {
        sampleManager.addChangedSample(this::getSampleUser, user -> user.setUsername(null));
        sampleManager.addChangedSample(this::getSampleUser, user -> user.setPassword(null));
        sampleManager.addChangedSample(this::getSampleUser, user -> user.setBirthday(null));
        sampleManager.addChangedSample(
                this::getSampleUser,
                user -> {
                    user.setUsername(null);
                    user.setPassword(null);
                    user.setBirthday(null);
                }
        );

        assertThrows(Exception.class, () -> userRepository.saveAll(invalidUsers));
    }

    @Test
    public void testSavingUsersWithInvalidBirthday() {
        LocalDate veryOldBirthday = LocalDate.of(1899, 12, 31);
        LocalDate futureBirthday = LocalDate.of(2049, 1, 1);

        sampleManager.addChangedSample(this::getSampleUser, user -> user.setBirthday(veryOldBirthday));
        sampleManager.addChangedSample(this::getSampleUser, user -> user.setBirthday(futureBirthday));

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAll(invalidUsers));
    }

    @Test
    public void testSavingUsersWithEqualUsernames() {
        sampleManager.addChangedSample(this::getSampleUser, user -> user.setPassword("123"));
        sampleManager.addChangedSample(this::getSampleUser, user -> user.setPassword("321"));

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAll(invalidUsers));
    }

    private User getSampleUser() {
        return new User(
                "John Doe",
                "123",
                LocalDate.of(1970, 1, 1),
                null,
                null,null
        );
    }
}
