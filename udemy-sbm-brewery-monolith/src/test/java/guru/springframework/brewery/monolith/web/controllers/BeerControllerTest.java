package guru.springframework.brewery.monolith.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.brewery.monolith.services.BeerService;
import guru.springframework.brewery.monolith.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static guru.springframework.brewery.monolith.config.WebConfig.BEER_PATH_V1;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    private BeerService beerServiceMock;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BeerDto beerDto;
    private UUID beerId;
    private String beerName;

    @BeforeEach
    void setUp() {
        beerId = UUID.randomUUID();
        beerName = "Test Dummy Beer";

        beerDto = BeerDto.builder()
                    .id(beerId)
                    .beerName(beerName)
                .build();
    }

    @Test
    void test_getBeerById() throws Exception {
        String jsonBeerDto = objectMapper.writeValueAsString(beerDto);

        when(beerServiceMock.getBeerById(beerId)).thenReturn(beerDto);

        mockMvc.perform(get(BEER_PATH_V1 + "/byId/" + beerId))
               .andExpect(status().isOk())
               .andExpect(content().json(jsonBeerDto));
    }

    @Test
    void test_getBeerById_throwsBadRequest() throws Exception {
        mockMvc.perform(get(BEER_PATH_V1 + "/byId/random_text"))
               .andExpect(status().isBadRequest());
    }

    @Test
    void test_getBeerByName() throws Exception {
        String jsonBeerDto = objectMapper.writeValueAsString(beerDto);

        when(beerServiceMock.getBeerByName(beerName)).thenReturn(beerDto);

        mockMvc.perform(get(BEER_PATH_V1 + "/byName/" + beerName))
               .andExpect(status().isOk())
               .andExpect(content().json(jsonBeerDto));
    }

    @Test
    void handlePost() throws Exception {
        String jsonBeerDto = objectMapper.writeValueAsString(beerDto);
        when(beerServiceMock.saveNewBeer(beerDto)).thenReturn(beerDto);

        mockMvc.perform(post(BEER_PATH_V1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonBeerDto))
               .andExpect(status().isCreated())
               .andExpect(header().string("Location", BEER_PATH_V1 + beerDto.getId()));
    }

    @Test
    void handleUpdate() throws Exception {
        String jsonBeerDto = objectMapper.writeValueAsString(beerDto);
        when(beerServiceMock.updateBeer(beerId, beerDto)).thenReturn(beerDto);

        mockMvc.perform(put(BEER_PATH_V1 + "/" + beerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonBeerDto))
               .andExpect(status().isNoContent());
    }

    @Test
    void deleteBeer() throws Exception {
        doNothing().when(beerServiceMock).deleteById(beerId);

        mockMvc.perform(delete(BEER_PATH_V1 + "/" + beerId))
                .andExpect(status().isNoContent());
    }
}