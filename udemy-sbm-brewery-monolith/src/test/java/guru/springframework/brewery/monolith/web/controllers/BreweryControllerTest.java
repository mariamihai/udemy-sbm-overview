package guru.springframework.brewery.monolith.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.brewery.monolith.domain.Brewery;
import guru.springframework.brewery.monolith.services.BreweryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static guru.springframework.brewery.monolith.config.WebConfig.BREWERY_PATH_V1;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BreweryController.class)
class BreweryControllerTest {

    @MockBean
    private BreweryService breweryServiceMock;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test_getAllBreweries() throws Exception {
        List<Brewery> givenBreweries = new ArrayList<>();
        String jsonBreweries = objectMapper.writeValueAsString(givenBreweries);

        when(breweryServiceMock.getAllBreweries()).thenReturn(givenBreweries);

        mockMvc.perform(get(BREWERY_PATH_V1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBreweries));
    }

    @Test
    void test_getBreweryById() throws Exception {
        UUID id = UUID.randomUUID();
        Brewery givenBrewery = Brewery.builder().id(id).build();
        String jsonBrewery = objectMapper.writeValueAsString(givenBrewery);


        when(breweryServiceMock.getBreweryById(id)).thenReturn(givenBrewery);

        mockMvc.perform(get(BREWERY_PATH_V1 + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBrewery));
    }

    @Test
    void test_getBreweryById_throwsBadRequest() throws Exception {
        mockMvc.perform(get(BREWERY_PATH_V1 + "/random_text"))
                .andExpect(status().isBadRequest());
    }
}