package group7.repositories;

import group7.entities.Address;
import group7.entities.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class AddressRepositoryTest {

    private List<Address> invalidAddresses;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        addressRepository.deleteAll();
        invalidAddresses = new ArrayList<>();
    }

    @Test
    public void testSavingAndRetrievingValidAddress() {
        User user = new User(null, "John Doe", "123", LocalDate.of(1970, 1, 1), null, null);
        User savedUser = userRepository.save(user);

        Address address = new Address(null, "Wall Street", "1", "12345", savedUser);
        Address savedAddress = addressRepository.save(address);
        Optional<Address> retrievedAddress = addressRepository.findById(savedAddress.getId());

        assertThat(retrievedAddress).isPresent();
    }

    @Test
    public void testSavingObjectWithEmptyValues() {
        invalidAddresses.add(new Address(null, "", "1", "12345", new User()));
        invalidAddresses.add(new Address(null, "Wall Street", "", "12345", new User()));
        invalidAddresses.add(new Address(null, "Wall Street", "1", "", new User()));
        invalidAddresses.add(new Address(null, "", "", "", new User()));

        assertThrows(ConstraintViolationException.class, () -> addressRepository.saveAll(invalidAddresses));
    }

    @Test
    public void testSavingObjectWithNullValues() {
        invalidAddresses.add(new Address(null, null, "1", "12345", new User()));
        invalidAddresses.add(new Address(null, "Wall Street", null, "12345", new User()));
        invalidAddresses.add(new Address(null, "Wall Street", "1", null, new User()));
        invalidAddresses.add(new Address(null, "Wall Street", "1", "12345", null));
        invalidAddresses.add(new Address(null, null, null, null, null));

        assertThrows(ConstraintViolationException.class, () -> addressRepository.saveAll(invalidAddresses));
    }

    @Test
    public void testAutoincrementId() {
        Address address = new Address(null, "Wall Street", "1", "12345", null);
        Address savedAddress = addressRepository.save(address);

        assertThat(savedAddress.getId()).isNotNull();
    }

    @Test
    public void testSavingObjectWithInvalidPostalCodeLength() {
        invalidAddresses.add(new Address(null, "Wall Street", "1", "123456", new User()));
        invalidAddresses.add(new Address(null, "Wall Street", "1", "1234", new User()));

        assertThrows(ConstraintViolationException.class, () -> addressRepository.saveAll(invalidAddresses));
    }
}
