package guru.springframework.sbmbrewery.services;

import guru.springframework.sbmbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerDto getCustomerById(UUID uuid) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .customerName("name")
                .build();
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void update(UUID customerId, CustomerDto customerDto) {
        // TODO - to be implemented
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting customer.");
    }
}
