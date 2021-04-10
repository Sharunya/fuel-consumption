package ru.drvshr.fuel_consumption.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.drvshr.fuel_consumption.BootApplication;
import ru.drvshr.fuel_consumption.enums.EFuelType;
import ru.drvshr.fuel_consumption.services.dto.Refueling;

@SuppressWarnings("ClassHasNoToStringMethod") // ни к чему
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class BaseTest {
    public static final int PRICE = 45;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private static int lastOdometer;
    private static LocalDate date = LocalDate.now();


    @BeforeClass
    public static void init() {
        lastOdometer = getRandomNumber(1000, 10000);
        date = LocalDate.of(2021, 1, 1);
    }

    @Before
    public void setUp() throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    protected Refueling fillRefuelingWithoutId() {
        return fillRefueling(null);
    }

    protected Refueling fillRefuelingWithRandomId() {
        return fillRefueling((long)getRandomNumber(0, 10000));
    }

    protected Refueling fillRefueling(Long id) {
        int price = getRandomNumber(43, 49);
        int tmpLastOdometer = lastOdometer;
        int odometer = lastOdometer + getRandomNumber(10, 1000);
        lastOdometer = odometer;
        date = date.plusDays(getRandomNumber(2, 12));
        int volume = getRandomNumber(5, 50);
        return (new Refueling()).setId(id)
                                .setDescription("description")
                                .setDate(date)
                                .setOdometer(odometer)
                                .setLastOdometer(tmpLastOdometer)
                                .setVolume(volume)
                                .setPrice(price)
                                .setCost(volume * price)
                                .setFuelType(EFuelType.RON95)
                                .setBrand(getString(getRandomNumber(5, 11)))
                                .setNotes(getString(getRandomNumber(5, 50)));
    }

    protected static int getRandomNumber(int min, int max) {
        return (int)((Math.random() * (max - min)) + min);
    }

    protected String getString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return generateString(chars, length);
    }

    private String generateString(String chars, int length) {
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < length) {
            int index = (int)(rnd.nextFloat() * chars.length());
            builder.append(chars.charAt(index));
        }
        return builder.toString();
    }

}
