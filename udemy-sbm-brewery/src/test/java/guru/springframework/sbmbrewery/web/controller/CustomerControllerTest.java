package guru.springframework.sbmbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sbmbrewery.services.CustomerService;
import guru.springframework.sbmbrewery.web.model.CustomerDto;
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
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    CustomerController classUnderTest;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto customer;

    private final static String CUSTOMER_URI = "/api/v1/customer";

    @Before
    public void setUp() {
        customer = CustomerDto.builder()
                .id(UUID.randomUUID())
                .customerName("Name")
                .build();
    }

    @Test
    public void test_getCustomer() throws Exception {
        when(customerService.getCustomerById(any(UUID.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_URI + "/" + customer.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(customer.getId().toString())))
            .andExpect(jsonPath("$.customerName", is("Name")));
    }

    @Test
    public void test_handlePost() throws Exception {
        String customerDtoJson = objectMapper.writeValueAsString(customer);

        when(customerService.saveNewCustomer(any(CustomerDto.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_URI).content(customerDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_handlePost_RequestBody() throws NoSuchMethodException {
        Method handlePost = classUnderTest.getClass().getDeclaredMethod("handlePost", CustomerDto.class);
        Annotation[][] parameterAnnotations = handlePost.getParameterAnnotations();
        RequestBody annotation = (RequestBody) parameterAnnotations[0][1];
        Assert.assertNotNull(annotation);
    }

    @Test
    public void test_handleUpdate() throws Exception {
        String customerDtoJson = objectMapper.writeValueAsString(customer);

        doNothing().when(customerService).update(any(UUID.class), any(CustomerDto.class));

        mockMvc.perform(MockMvcRequestBuilders.put(CUSTOMER_URI + "/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_handleUpdate_RequestBody() throws NoSuchMethodException {
        Method handleUpdate = classUnderTest.getClass().getDeclaredMethod("handleUpdate", UUID.class, CustomerDto.class);
        Annotation[][] parameterAnnotations = handleUpdate.getParameterAnnotations();
        RequestBody annotation = (RequestBody) parameterAnnotations[1][1];
        Assert.assertNotNull(annotation);
    }

    @Test
    public void test_deleteBeer() throws Exception {
        doNothing().when(customerService).update(any(UUID.class), any(CustomerDto.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(CUSTOMER_URI + "/" + customer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
