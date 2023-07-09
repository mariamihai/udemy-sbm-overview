package guru.springframework.brewery.monolith.services;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.repositories.BeerRepository;
import guru.springframework.brewery.monolith.web.controllers.NotFoundException;
import guru.springframework.brewery.monolith.web.mappers.BeerMapper;
import guru.springframework.brewery.monolith.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BeerServiceImplTest {

    @Mock
    private BeerRepository beerRepositoryMock;

    @Mock
    private BeerMapper beerMapperMock;

    @InjectMocks
    private BeerServiceImpl classUnderTest;

    private Beer beer;
    private BeerDto beerDto;
    private UUID beerId;
    private String beerName;

    @BeforeEach
    void setUp() {
        beerId = UUID.randomUUID();
        beerName = "Test beer";

        beer = Beer.builder()
                .id(beerId)
                .beerName(beerName)
                .build();

        beerDto = BeerDto.builder()
                .id(beerId)
                .beerName(beerName)
                .build();
    }

    @Test
    void test_getBeerById() {
        when(beerRepositoryMock.findById(beerId)).thenReturn(Optional.of(beer));
        when(beerMapperMock.beerToBeerDto(beer)).thenReturn(beerDto);

        BeerDto actual = classUnderTest.getBeerById(beerId);

        assertEquals(beerDto, actual);
    }

    @Test
    void test_getBeerByName() {
        when(beerRepositoryMock.findByBeerName(beerName)).thenReturn(beer);
        when(beerMapperMock.beerToBeerDto(beer)).thenReturn(beerDto);

        BeerDto actual = classUnderTest.getBeerByName(beerName);

        assertEquals(beerDto, actual);
    }

    @Test
    void test_getBeerByName_throwsNotFoundException() {
        when(beerRepositoryMock.findByBeerName(beerName)).thenReturn(null);
        when(beerMapperMock.beerToBeerDto(beer)).thenReturn(beerDto);

        assertThrows(NotFoundException.class,
                () -> classUnderTest.getBeerByName(beerName));
    }

    @Test
    void test_saveNewBeer() {
        when(beerMapperMock.beerDtoToBeer(beerDto)).thenReturn(beer);
        when(beerRepositoryMock.save(beer)).thenReturn(beer);
        when(beerMapperMock.beerToBeerDto(beer)).thenReturn(beerDto);

        BeerDto actual = classUnderTest.saveNewBeer(this.beerDto);

        assertEquals(beerDto, actual);
    }

    @Test
    void test_updateBeer() {
        String expectedBeerName = "Updated Dummy Beer";
        beerDto.setBeerName(expectedBeerName);

        when(beerRepositoryMock.findById(beerId)).thenReturn(Optional.of(beer));
        when(beerRepositoryMock.save(beer)).thenReturn(beer);
        when(beerMapperMock.beerToBeerDto(beer)).thenReturn(beerDto);

        BeerDto actualBeerDto = classUnderTest.updateBeer(beerId, this.beerDto);

        assertEquals(expectedBeerName, actualBeerDto.getBeerName());
    }

    @Test
    void test_deleteById() {
        when(beerRepositoryMock.findById(beerId)).thenReturn(Optional.of(beer));
        doNothing().when(beerRepositoryMock).deleteById(beerId);

        classUnderTest.deleteById(beerId);

        verify(beerRepositoryMock).deleteById(beerId);
    }

    @Test
    void test_validatedBeerById() {
        when(beerRepositoryMock.findById(beerId)).thenReturn(Optional.of(beer));

        Beer actual = classUnderTest.validatedBeerById(beerId);

        assertEquals(beer, actual);
    }

    @Test
    void test_validatedBeerById_throwsException() {
        when(beerRepositoryMock.findById(beerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                     () -> classUnderTest.validatedBeerById(beerId));
    }
}