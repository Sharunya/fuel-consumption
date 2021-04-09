package ru.drvshr.fuel_consumption.controllers;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javassist.NotFoundException;
import lombok.Getter;
import ru.drvshr.fuel_consumption.model.RefuelingEntity;
import ru.drvshr.fuel_consumption.repositories.IRefuelingRepository;
import ru.drvshr.fuel_consumption.services.RefuelingService;
import ru.drvshr.fuel_consumption.services.dto.Refueling;

public class RefuelingControllerTest extends BaseTest {
    private static final String URIRoot = "/refueling";
    private static boolean isInitDB = false;

    @Autowired
    @Getter
    private IRefuelingRepository refuelingRepository;

    @Autowired
    @Getter
    private RefuelingService refuelingService;

    /** Подготавливаем БД */
    @Before
    public void initDB() {
        if (isInitDB) {
            return;
        }
        Stream //
               .iterate(1L, n -> n + 1L) //
               .limit(10) //
               .map((Long t) -> fillRefuelingWithoutId()) //
               .forEach(r -> {
                   try {
                       refuelingService.saveRefueling(r);
                   }
                   catch (NotFoundException ignored) {
                   }
               });
        isInitDB = true;
    }

    /* beginning testing "SAVE" ------------------------------------------------------------------------------------ */

    @Test
    public void tryingToSaveAnEmptyOneWithAnError() throws Exception {
        //given
        String uriOperation = "/save";

        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put(URIRoot + uriOperation));
        //then
        resultActions //
                      .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void tryingToSaveWithANonExistentId() throws Exception {
        //given
        String uriOperation = "/save";

        Refueling refueling = fillRefuelingWithRandomId();
        String inputJson = mapToJson(refueling);

        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders //
                                                                         .put(URIRoot + uriOperation)//
                                                                         .contentType(MediaType.APPLICATION_JSON_VALUE) //
                                                                         .content(inputJson));
        //then
        //noinspection ConstantConditions (не может быть)
        resultActions //
                      .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void successfulSave() throws Exception {
        //given
        String uriOperation = "/save";

        Refueling refuelingRequest = fillRefuelingWithoutId();
        String inputJson = mapToJson(refuelingRequest);
        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders //
                                                                         .put(URIRoot + uriOperation)//
                                                                         .contentType(MediaType.APPLICATION_JSON_VALUE) //
                                                                         .content(inputJson));
        Refueling refuelingResponse = mapFromJson(resultActions.andReturn().getResponse().getContentAsString(), Refueling.class);
        //then
        resultActions //
                      .andExpect(MockMvcResultMatchers.status().isOk());
        assertTrue(refuelingRequest.getDescription().equals(refuelingResponse.getDescription()) //
                   && refuelingRequest.getDate().equals(refuelingResponse.getDate()));
    }

    @Test
    public void successfulEdit() throws Exception {
        //given
        String uriOperation = "/save";

        /* Выбираем один первый */
        RefuelingEntity refuelingEntity = refuelingRepository.findAll().iterator().next();

        /* Изменяем полученный из БД объект */
        Refueling refuelingRequest = RefuelingService //
                                                      .mappingRefuelingEntityToRequest(refuelingEntity) //
                                                      .setNotes(getString(getRandomNumber(5, 50)));

        String inputJson = mapToJson(refuelingRequest);
        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders //
                                                                         .put(URIRoot + uriOperation)//
                                                                         .contentType(MediaType.APPLICATION_JSON_VALUE) //
                                                                         .content(inputJson));
        Refueling refuelingResponse = mapFromJson(resultActions.andReturn().getResponse().getContentAsString(), Refueling.class);
        //then
        resultActions //
                      .andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(refuelingRequest.getNotes(), refuelingResponse.getNotes());
    }

    /* end of testing "SAVE" ----------------------------------------------------------------------------------------- */

    /* beginning testing "SEARCH" ------------------------------------------------------------------------------------ */

    @Test
    public void successfulSearch() throws Exception {
        //given
        String uriOperation = "/search";

        Refueling refuelingRequest = RefuelingService.mappingRefuelingEntityToRequest(refuelingRepository.findAll().iterator().next());
        String inputJson = mapToJson(refuelingRequest);
        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders //
                                                                         .get(MessageFormat.format("{0}{1}/{2}", URIRoot, uriOperation, refuelingRequest.getId()))//
                                                                         .contentType(MediaType.APPLICATION_JSON_VALUE) //
                                                                         .content(inputJson));
        Refueling refuelingResponse = mapFromJson(resultActions.andReturn().getResponse().getContentAsString(), Refueling.class);
        //then
        resultActions //
                      .andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(refuelingRequest, refuelingResponse);
    }

    /* end of testing "SEARCH" ------------------------------------------------------------------------------------ */

    /* beginning testing "GETALL" ------------------------------------------------------------------------------------ */

    @Test
    public void successfulGetAll() throws Exception {
        //given
        String uriOperation = "/getall";

        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders //
                                                                         .get(MessageFormat.format("{0}{1}", URIRoot, uriOperation))//
                                                                         .contentType(MediaType.APPLICATION_JSON_VALUE));
        List<Refueling> refuelingResponse = mapFromJson(resultActions.andReturn().getResponse().getContentAsString(), List.class);
        //then
        resultActions //
                      .andExpect(MockMvcResultMatchers.status().isOk());
//        assertTrue(refuelingResponse != null && refuelingResponse.getId() != null);
    }

    /* end of testing "GETALL" ------------------------------------------------------------------------------------ */

}
