package guru.springframework.brewery.monolith.services;

import guru.springframework.brewery.monolith.domain.Brewery;
import guru.springframework.brewery.monolith.repositories.BreweryRepository;
import guru.springframework.brewery.monolith.web.controllers.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BreweryServiceImplTest {

    @Mock
    private BreweryRepository breweryRepositoryMock;

    @InjectMocks
    private BreweryServiceImpl classUnderTest;

    @Test
    void test_getAllBreweries() {
        List<Brewery> givenBreweries = new ArrayList<>();
        when(breweryRepositoryMock.findAll()).thenReturn(givenBreweries);

        List<Brewery> allBreweries = classUnderTest.getAllBreweries();

        assertEquals(givenBreweries, allBreweries);
    }

    @Test
    void test_getBreweryById() {
        Brewery givenBrewery = Brewery.builder().build();
        Optional<Brewery> optionalBrewery = Optional.of(givenBrewery);

        when(breweryRepositoryMock.findById(any())).thenReturn(optionalBrewery);

        Brewery brewery = classUnderTest.getBreweryById(any());

        assertEquals(givenBrewery, brewery);
    }
    @Test
    void test_getBreweryById_throwsNotFoundException() {
        Optional<Brewery> optionalBrewery = Optional.empty();
        when(breweryRepositoryMock.findById(any())).thenReturn(optionalBrewery);

        assertThrows(NotFoundException.class, () -> classUnderTest.getBreweryById(any()));
    }

}