package ua.pt.UnitTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.pt.controllers.CurrencyController;
import ua.pt.service.CurrencyService;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyService service;
    

    @Test
    void whenGetConvertedPrice_thenGetPrice() throws Exception{
        double price = 2.5;

        when(service.convertPrice(price, "EUR")).thenReturn(2.7);

        mvc.perform(
            get("/api/currency/EUR?price=2.5")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(String.valueOf(2.7)));

        verify(service, times(1)).convertPrice(price, "EUR");
    }
}
