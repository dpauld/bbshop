package group7.repositories;

import group7.entities.Address;
import group7.entities.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class AddressRepositoryUnitTest {

    private List<Address> invalidAddresses;
    private SampleManager<Address> sampleManager;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        addressRepository.deleteAll();
        invalidAddresses = new ArrayList<>();
        sampleManager = new SampleManager<>(invalidAddresses);
    }

    @Test
    public void testSavingAndRetrievingValidAddress() {
        Address address = getSampleAddress();
        Address savedAddress = addressRepository.save(address);
        Optional<Address> retrievedAddress = addressRepository.findById(savedAddress.getId());

        assertThat(retrievedAddress).isPresent();
    }

    @Test
    public void testSavingAddressWithEmptyValues() {
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setStreet(""));
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setNumber(""));
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setPostalCode(""));
        sampleManager.addChangedSample(
                this::getSampleAddress,
                address -> {
                    address.setStreet("");
                    address.setNumber("");
                    address.setPostalCode("");
                }
        );

        assertThrows(ConstraintViolationException.class, () -> addressRepository.saveAll(invalidAddresses));
    }

    @Test
    public void testSavingAddressWithNullValues() {
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setStreet(null));
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setNumber(null));
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setPostalCode(null));
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setUser(null));
        sampleManager.addChangedSample(
                this::getSampleAddress,
                address -> {
                    address.setStreet(null);
                    address.setNumber(null);
                    address.setPostalCode(null);
                    address.setUser(null);
                }
        );

        assertThrows(ConstraintViolationException.class, () -> addressRepository.saveAll(invalidAddresses));
    }

    @Test
    public void testAutoincrementId() {
        Address address = getSampleAddress();
        Address savedAddress = addressRepository.save(address);

        assertThat(savedAddress.getId()).isNotNull();
    }

    @Test
    public void testSavingAddressWithInvalidPostalCodeLength() {
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setPostalCode("123456"));
        sampleManager.addChangedSample(this::getSampleAddress, address -> address.setPostalCode("1234"));

        assertThrows(ConstraintViolationException.class, () -> addressRepository.saveAll(invalidAddresses));
    }

    private Address getSampleAddress() {
        userRepository.deleteAll();
        User user = new User(
                "John Doe",
                "123",
                LocalDate.of(1970, 1, 1),
                null,
                null
        );
        User savedUser = userRepository.save(user);

        return new Address("Wall Street", "1", "12345", savedUser);
    }
}
