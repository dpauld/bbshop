package group7.service;

import group7.dto.AddressRequestDto;
import group7.entity.Address;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
public class AddressServiceUnitTest {

    @Autowired
    private AddressService addressService;

    private Address createdAddress;

    @BeforeEach
    public void init() {
        AddressRequestDto address = new AddressRequestDto();
        address.setStreet("Metusaliquet");
        address.setNumber("10");
        address.setPostalCode("96028");

        createdAddress = addressService.createAddress(address);
    }

    @Test
    @Transactional
    public void testCreateAddress() {
        assertNotNull(createdAddress);
    }

    @Test
    @Transactional
    public void testFindAddressById() {
        assertNotNull(addressService.getAddressById(createdAddress.getId()));
    }

    @Test
    @Transactional
    public void testFindAllAddresses() {
        AddressRequestDto address = getSampleAddress();

        addressService.createAddress(address);

        List<Address> addresses = addressService.getAllAddresses();
        assertEquals(2, addresses.size());
    }

    @Test
    @Transactional
    public void testUpdateAddress() {
        AddressRequestDto newAddress = getSampleAddress();

        addressService.updateAddressById(createdAddress.getId(), newAddress);
        assertEquals(newAddress.getStreet(), addressService.getAddressById(createdAddress.getId()).getStreet());
    }

    private AddressRequestDto getSampleAddress() {
        AddressRequestDto address = new AddressRequestDto();
        address.setStreet("Quisdictum");
        address.setNumber("7");
        address.setPostalCode("96028");

        return address;
    }

    @Test
    @Transactional
    public void testDeleteAddress() {
        addressService.deleteAddressById(createdAddress.getId());

        assertEquals(0, addressService.getAllAddresses().size());
    }
}
