package zoo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import zoo.domain.animals.AnimalFactory;
import zoo.domain.animals.AnimalSex;
import zoo.domain.schedule.FeedingScheduleFactory;
import zoo.repository.AnimalRepository;
import zoo.repository.FeedingScheduleRepository;
import zoo.web.dto.requests.ScheduleCreateRequest;
import zoo.web.dto.requests.ScheduleUpdateRequest;
import zoo.web.dto.responses.ScheduleResponse;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalFactory animalFactory;

    @Autowired
    private FeedingScheduleFactory feedingScheduleFactory;

    @Autowired
    private FeedingScheduleRepository feedingScheduleRepository;

    @Test
    @DisplayName("Создание расписания")
    void createSchedule_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        var tmp = animalRepository.save(animal);

        var request = ScheduleCreateRequest.builder()
                                           .animalId(tmp.getId())
                                           .foodName("TestFood")
                                           .dailyTime(LocalTime.MIN)
                                           .build();

        String responseJson = mockMvc.perform(post("/api/schedule/create").contentType(MediaType.APPLICATION_JSON)
                                                                          .content(objectMapper.writeValueAsString(
                                                                                  request)))
                                     .andExpect(status().isCreated())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        var response = objectMapper.readValue(responseJson, ScheduleResponse.class);

        Assertions.assertAll(
                () -> assertNotNull(response.id()),
                () -> assertEquals(tmp.getId(), response.animalId()),

                () -> assertEquals(request.dailyTime(), response.dailyTime()),
                () -> assertEquals(request.foodName(), response.foodName())
        );
    }

    @Test
    @DisplayName("Обновление расписания")
    void updateSchedule_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        var tmp = animalRepository.save(animal);

        var schedule = feedingScheduleRepository.save(feedingScheduleFactory.createSchedule(
                tmp,
                LocalTime.MIN,
                "TestFood"
        ));

        schedule.setFoodName("TestFood2");
        schedule.setAnimal(animal);
        schedule.setSchedule(LocalTime.MAX);

        var request = ScheduleUpdateRequest.builder()
                                           .scheduleId(schedule.getId())
                                           .dailyTime(schedule.getSchedule())
                                           .foodName(schedule.getFoodName())
                                           .build();

        String responseJson = mockMvc.perform(post("/api/schedule/update").contentType(MediaType.APPLICATION_JSON)
                                                                          .content(objectMapper.writeValueAsString(
                                                                                  request)))
                                     .andExpect(status().isOk())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        var response = objectMapper.readValue(responseJson, ScheduleResponse.class);

        final var fin = schedule;

        Assertions.assertAll(
                () -> assertEquals(fin.getId(), response.id()),
                () -> assertEquals(fin.getSchedule(), response.dailyTime()),
                () -> assertEquals(fin.getAnimal().getId(), response.animalId()),
                () -> assertEquals(fin.getFoodName(), response.foodName())
        );
    }


    @Test
    @DisplayName("Удаление расписания")
    void removeSchedule_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        var tmp = animalRepository.save(animal);

        var schedule = feedingScheduleRepository.save(feedingScheduleFactory.createSchedule(
                tmp,
                LocalTime.MIN,
                "TestFood"
        ));
        mockMvc.perform(delete("/api/schedule/" + schedule.getId())).andExpect(status().isOk());

        assertNull(feedingScheduleRepository.getScheduleById(schedule.getId()));
    }

    @Test
    @DisplayName("Получение расписания")
    void getSchedule_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        var tmp = animalRepository.save(animal);

        var schedule = feedingScheduleRepository.save(feedingScheduleFactory.createSchedule(
                tmp,
                LocalTime.MIN,
                "TestFood"
        ));

        var json = mockMvc.perform(get("/api/schedule/" + schedule.getId()))
                          .andExpect(status().isOk())
                          .andReturn()
                          .getResponse()
                          .getContentAsString();

        var response = objectMapper.readValue(json, ScheduleResponse.class);

        Assertions.assertAll(
                () -> assertEquals(schedule.getId(), response.id()),
                () -> assertEquals(schedule.getFoodName(), response.foodName())
        );
    }

    @Test
    @DisplayName("Получение полного расписания")
    void getAllSchedules_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        var tmp = animalRepository.save(animal);

        feedingScheduleRepository.save(feedingScheduleFactory.createSchedule(tmp, LocalTime.MIN, "TestFood"));

        var json = mockMvc.perform(get("/api/schedule/all"))
                          .andExpect(status().isOk())
                          .andReturn()
                          .getResponse()
                          .getContentAsString();

        var resp = objectMapper.readValue(json, new TypeReference<List<ScheduleResponse>>() {});

        assertFalse(resp.isEmpty());
    }
}
