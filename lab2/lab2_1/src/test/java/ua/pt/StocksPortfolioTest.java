package ua.pt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    // Create mock object
    @InjectMocks
    StocksPortfolio stocksPortfolio;

    // Add mock to the object
    @Mock
    IStockmarketService iStockmarketService;

    @Test
    void testTotalValue(){
        // add what to expect when loading them
        when(iStockmarketService.lookUpPrice("XPTO")).thenReturn(59.5);
        when(iStockmarketService.lookUpPrice("DELL")).thenReturn(10.00);

        // execute test
        stocksPortfolio.addStock(new Stock("XPTO", 1));
        stocksPortfolio.addStock(new Stock("DELL", 2));
        double total = stocksPortfolio.totalValue();

        // check results
        assertEquals(79.5, total);
        verify(iStockmarketService, times(2)).lookUpPrice(anyString());

        // using hamcrest assertions
        assertThat(79.5, equalTo(total));
    }
}
