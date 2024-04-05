package ua.pt.UnitTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pt.service.ExternalApiService;
import ua.pt.service.impl.CurrencyServiceImpl;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {
    @Mock
    private ExternalApiService externalApiService;

    @InjectMocks
    private CurrencyServiceImpl currencyServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(CurrencyServiceTest.class);

    @Test
    void whenConvertPrice_thenReturnConvertedPrice() {
        logger.info("===========================================");
        logger.info("Starting test whenConvertPrice_thenReturnConvertedPrice...");

        String response = "{data: {USD: 1.0, EUR: 0.5}}";

        when(externalApiService.getCurrencies()).thenReturn(response);

        double price = 2.0;

        long start = System.nanoTime();
        double convertedPrice = currencyServiceImpl.convertPrice(2.0, "EUR");
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        logger.info("Converting " + price + " USD to - " + convertedPrice + " EUR - it took: " + timeElapsed + " ms");

        assertThat(convertedPrice).isEqualTo(1.0);

        start = System.nanoTime();
        convertedPrice = currencyServiceImpl.convertPrice(2.0, "EUR");
        finish = System.nanoTime();
        timeElapsed = finish - start;
        logger.info("Converting " + price + " USD to - " + convertedPrice + " EUR - it took: " + timeElapsed + " ms");

        start = System.nanoTime();
        convertedPrice = currencyServiceImpl.convertPrice(2.0, "USD");
        finish = System.nanoTime();
        timeElapsed = finish - start;
        logger.info("Converting " + price + " USD to - " + convertedPrice + " USD - it took: " + timeElapsed + " ms");

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }

    @Test
    void whenGetExchangeRate_thenReturnExchangeRate() {
        logger.info("===========================================");
        logger.info("Starting test whenGetExchangeRate_thenReturnExchangeRate...");
        
        String response = "{data: {USD: 1.0, EUR: 0.5}}";

        when(externalApiService.getCurrencies()).thenReturn(response);

        double exchangeRate = currencyServiceImpl.getExchangeRate("EUR");

        assertThat(exchangeRate).isEqualTo(0.5);

        logger.info("Test completed successfully.");
        logger.info("===========================================");
    }
}
