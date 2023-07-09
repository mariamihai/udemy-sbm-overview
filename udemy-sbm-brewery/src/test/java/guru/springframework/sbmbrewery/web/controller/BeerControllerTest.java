package guru.springframework.sbmbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sbmbrewery.services.BeerService;
import guru.springframework.sbmbrewery.web.model.BeerDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    BeerController classUnderTest;

    BeerDto beer;
    BeerDto sentBeer;

    private final static String BEER_URI = "/api/v1/beer";

    @Before
    public void setUp() {
        beer = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Beer")
                .beerStyle("Type")
                .upc(1234567890L)
                .build();

        sentBeer = BeerDto.builder()
                .beerName("Beer")
                .beerStyle("Type")
                .upc(1234567890L)
                .build();
    }

    @Test
    public void test_getBeer() throws Exception {
        when(beerService.getBeerById(any(UUID.class))).thenReturn(beer);

        mockMvc.perform(MockMvcRequestBuilders.get(BEER_URI + "/" + beer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer")));
    }

    @Test
    public void test_handlePost() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(sentBeer);

        when(beerService.saveNewBeer(any(BeerDto.class))).thenReturn(beer);

        mockMvc.perform(MockMvcRequestBuilders.post(BEER_URI).content(beerDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_handlePost_RequestBody() throws NoSuchMethodException {
        Method handlePost = classUnderTest.getClass().getDeclaredMethod("handlePost", BeerDto.class);
        Annotation[][] parameterAnnotations = handlePost.getParameterAnnotations();
        RequestBody annotation = (RequestBody) parameterAnnotations[0][1];
        Assert.assertNotNull(annotation);
    }

    @Test
    public void test_handleUpdate() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(sentBeer);

        doNothing().when(beerService).update(any(UUID.class), any(BeerDto.class));

        mockMvc.perform(MockMvcRequestBuilders.put(BEER_URI + "/" + beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_handleUpdate_RequestBody() throws NoSuchMethodException {
        Method handleUpdate = classUnderTest.getClass().getDeclaredMethod("handleUpdate", UUID.class, BeerDto.class);
        Annotation[][] parameterAnnotations = handleUpdate.getParameterAnnotations();
        RequestBody annotation = (RequestBody) parameterAnnotations[1][1];
        Assert.assertNotNull(annotation);
    }

    @Test
    public void test_deleteBeer() throws Exception {
        doNothing().when(beerService).update(any(UUID.class), any(BeerDto.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(BEER_URI + "/" + beer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
