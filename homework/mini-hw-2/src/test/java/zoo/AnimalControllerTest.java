package zoo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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
import zoo.domain.enclosures.EnclosureFactory;
import zoo.domain.reports.Report;
import zoo.repository.AnimalRepository;
import zoo.repository.EnclosureRepository;
import zoo.web.dto.requests.AnimalCreateRequest;
import zoo.web.dto.requests.AnimalFeedRequest;
import zoo.web.dto.requests.AnimalMoveRequest;
import zoo.web.dto.requests.AnimalUpdateRequest;
import zoo.web.dto.responses.AnimalResponse;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalFactory animalFactory;

    @Autowired
    private EnclosureFactory enclosureFactory;

    @Autowired
    private EnclosureRepository enclosureRepository;

    @Test
    @DisplayName("Создание животного")
    void createAnimal_ValidData() throws Exception {
        var request = AnimalCreateRequest.builder()
                                         .sex("male")
                                         .birthday(LocalDate.EPOCH)
                                         .favoriteFood("Book")
                                         .type("fish")
                                         .isHealthy(false)
                                         .name("Ficus")
                                         .build();

        String responseJson = mockMvc.perform(post("/api/animals/create").contentType(MediaType.APPLICATION_JSON)
                                                                         .content(objectMapper.writeValueAsString(
                                                                                 request)))
                                     .andExpect(status().isCreated())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        var response = objectMapper.readValue(responseJson, AnimalResponse.class);
        Assertions.assertAll(
                () -> assertNotNull(response.id()),
                () -> assertNull(response.encloseId()),

                () -> assertEquals(request.birthday(), response.birthday()),
                () -> assertEquals(request.name(), response.name()),
                () -> assertEquals(request.sex(), response.sex()),
                () -> assertEquals(request.isHealthy(), response.isHealthy()),
                () -> assertEquals(request.favoriteFood(), response.favoriteFood()),
                () -> assertEquals(request.type(), response.type())
        );
    }

    @Test
    @DisplayName("Кормление животного")
    void feedAnimal_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        animal = animalRepository.save(animal);

        var request = AnimalFeedRequest.builder().foodName("Eda").id(animal.getId()).build();

        String responseJson = mockMvc.perform(post("/api/animals/feed").contentType(MediaType.APPLICATION_JSON)
                                                                       .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isOk())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        assertTrue(responseJson.isEmpty());

        responseJson = mockMvc.perform(get("/api/stats/report/feed").contentType(MediaType.APPLICATION_JSON)
                                                                    .content(objectMapper.writeValueAsString(request)))
                              .andExpect(status().isOk())
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
        var report = objectMapper.readValue(responseJson, Report.class);

        assertTrue(report.content().contains("Eda"));
    }


    @Test
    @DisplayName("обновление данных о  животном")
    void updateAnimal_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        animal = animalRepository.save(animal);

        animal.setFavoriteFood("ReleaseFood");

        var request = AnimalUpdateRequest.builder().id(animal.getId()).favoriteFood(animal.getFavoriteFood()).build();

        String responseJson = mockMvc.perform(post("/api/animals/update").contentType(MediaType.APPLICATION_JSON)
                                                                         .content(objectMapper.writeValueAsString(
                                                                                 request)))
                                     .andExpect(status().isOk())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        var response = objectMapper.readValue(responseJson, AnimalResponse.class);

        final var finalAnimal = animal;

        Assertions.assertAll(
                () -> assertEquals(finalAnimal.getId(), response.id()),

                () -> assertEquals(finalAnimal.getName(), response.name()),
                () -> assertEquals(request.favoriteFood(), response.favoriteFood())
        );
    }


    @Test
    @DisplayName("Перемещение животного между вольерами")
    void moveAnimal_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        animal = animalRepository.save(animal);

        var enclosure = enclosureFactory.createEnclosure("TestEnclosure", 42, 100);
        enclosure = enclosureRepository.save(enclosure);

        var request = AnimalMoveRequest.builder().animalId(animal.getId()).enclosureId(enclosure.getId()).build();

        String responseJson = mockMvc.perform(post("/api/animals/move").contentType(MediaType.APPLICATION_JSON)
                                                                       .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isOk())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        var response = objectMapper.readValue(responseJson, AnimalResponse.class);

        final var finalAnimal = animal;
        final var finalEnclosure = enclosure;

        Assertions.assertAll(
                () -> assertEquals(finalAnimal.getId(), response.id()),

                () -> assertEquals(finalAnimal.getName(), response.name()),
                () -> assertEquals(finalEnclosure.getId(), response.encloseId())
        );
    }


    @Test
    @DisplayName("Удаление животного")
    void removeAnimal_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        var tmp = animalRepository.save(animal);
        mockMvc.perform(delete("/api/animals/" + tmp.getId())).andExpect(status().isOk());

        Assertions.assertAll(() -> assertNull(animalRepository.getAnimalById(tmp.getId())));
    }

    @Test
    @DisplayName("Получение животного")
    void getAnimal_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        final var tmp = animalRepository.save(animal);

        var json = mockMvc.perform(get("/api/animals/" + tmp.getId()))
                          .andExpect(status().isOk())
                          .andReturn()
                          .getResponse()
                          .getContentAsString();

        var response = objectMapper.readValue(json, AnimalResponse.class);

        Assertions.assertAll(
                () -> assertEquals(tmp.getId(), response.id()),
                () -> assertNull(response.encloseId()),

                () -> assertEquals(animal.getBirthday(), response.birthday()),
                () -> assertEquals(animal.getName(), response.name()),
                () -> assertEquals(animal.getSex().getName(), response.sex()),
                () -> assertEquals(animal.getHealthy(), response.isHealthy()),
                () -> assertEquals(animal.getFavoriteFood(), response.favoriteFood()),
                () -> assertEquals(animal.getType(), response.type())
        );
    }

    @Test
    @DisplayName("Получение всех животных")
    void getAllAnimals_ValidData() throws Exception {
        var animal = animalFactory.createAnimal(
                "TestType",
                "TestName",
                LocalDate.of(2025, 11, 12),
                AnimalSex.FEMALE,
                "TestFood",
                true
        );
        animalRepository.save(animal);

        var json = mockMvc.perform(get("/api/animals/all"))
                          .andExpect(status().isOk())
                          .andReturn()
                          .getResponse()
                          .getContentAsString();

        var resp = objectMapper.readValue(json, new TypeReference<List<AnimalResponse>>() {});

        assertFalse(resp.isEmpty());
    }
}
