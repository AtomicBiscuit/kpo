package hse.kpo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.domains.cars.Car;
import hse.kpo.dto.CarResponse;
import hse.kpo.enums.EngineTypes;
import hse.kpo.facade.Hse;
import hse.kpo.storages.CarStorage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class MVCTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    Hse hseFacade;

    @Autowired
    CarStorage carStorage;

    Random gen = new Random(5);

    @Test
    @DisplayName("Create 10 random cars")
    void CreateTenCars_SuccessfullTest() throws Exception {
        var engines = Arrays.stream(EngineTypes.values()).toList();
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cars.add(createCar(engines.get(gen.nextInt(engines.size()))));
        }

        var cars1 = cars.stream().map(car -> new CarResponse(car.getVin(), car.getEngineType(), 0)).toList();

        carStorage.getCars().clear();
        carStorage.setCarNumberCounter(0);

        for (var car : cars1) {
            var result = mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON)
                                                      .content(mapper.writeValueAsString(car)))
                            .andExpect(status().isCreated())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();
            CarResponse createdCar = mapper.readValue(result, CarResponse.class);
            Assertions.assertEquals(car.vin(), createdCar.vin());
            Assertions.assertEquals(car.engineType(), createdCar.engineType());
        }
    }

    private Car createCar(EngineTypes engineType) {
        int pedalSize = gen.nextInt(1, 15);
        return switch (engineType) {
            case EngineTypes.PEDAL -> hseFacade.addPedalCar(pedalSize);
            case EngineTypes.HAND -> hseFacade.addHandCar();
            case EngineTypes.LEVITATION -> hseFacade.addLevitationCar();
        };
    }
}
