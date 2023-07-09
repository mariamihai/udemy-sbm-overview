package guru.springframework.sbmbrewery.web.mappers;

import guru.springframework.sbmbrewery.domain.Customer;
import guru.springframework.sbmbrewery.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);
    Customer customerToCustomerDto(CustomerDto customerDto);
}
