package guru.springframework.brewery.monolith.web.mappers;

import guru.springframework.brewery.monolith.domain.Beer;
import guru.springframework.brewery.monolith.domain.BeerOrderLine;
import guru.springframework.brewery.monolith.repositories.BeerRepository;
import guru.springframework.brewery.monolith.web.model.BeerOrderLineDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BeerOrderLineMapperDecoratorTest {

    @Mock
    private BeerRepository beerRepositoryMock;
    @Mock
    private BeerOrderLineMapper beerOrderLineMapperMock;

    private BeerOrderLineMapperDecorator classUnderTest;

    private BeerOrderLine line;
    private BeerOrderLineDto lineDto;

    private UUID beerId;
    private Beer beer;

    @BeforeEach
    void setUp() {
        classUnderTest = mock(BeerOrderLineMapperDecorator.class, CALLS_REAL_METHODS);
        classUnderTest.setBeerRepository(beerRepositoryMock);
        classUnderTest.setBeerOrderLineMapper(beerOrderLineMapperMock);

        line = BeerOrderLine.builder().build();
        lineDto = BeerOrderLineDto.builder().build();

        beerId = UUID.randomUUID();
        beer = Beer.builder().id(beerId).build();
    }

    @Test
    void test_beerOrderLineToDto() {
        line.setBeer(beer);
        when(beerOrderLineMapperMock.beerOrderLineToDto(line)).thenReturn(lineDto);

        BeerOrderLineDto actual = classUnderTest.beerOrderLineToDto(line);

        assertEquals(beerId, actual.getBeerId());
    }

    @Test
    void test_dtoToBeerOrderLine() {
        lineDto.setBeerId(beerId);

        when(beerOrderLineMapperMock.dtoToBeerOrderLine(lineDto)).thenReturn(line);
        when(beerRepositoryMock.getOne(beerId)).thenReturn(beer);

        BeerOrderLine actual = classUnderTest.dtoToBeerOrderLine(lineDto);

        assertEquals(0, actual.getQuantityAllocated());
        assertEquals(beer, actual.getBeer());
    }
}