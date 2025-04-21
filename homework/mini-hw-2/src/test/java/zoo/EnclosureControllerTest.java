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
import zoo.domain.enclosures.EnclosureFactory;
import zoo.repository.AnimalRepository;
import zoo.repository.EnclosureRepository;
import zoo.web.dto.requests.EnclosureCreateRequest;
import zoo.web.dto.requests.EnclosureUpdateRequest;
import zoo.web.dto.responses.EnclosureResponse;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EnclosureControllerTest {
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
    @DisplayName("Создание вольера")
    void createEnclosure_ValidData() throws Exception {
        var request = EnclosureCreateRequest.builder().capacity(42).size(42).type("Test").build();

        String responseJson = mockMvc.perform(post("/api/enclosure/create").contentType(MediaType.APPLICATION_JSON)
                                                                           .content(objectMapper.writeValueAsString(
                                                                                   request)))
                                     .andExpect(status().isCreated())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        var response = objectMapper.readValue(responseJson, EnclosureResponse.class);

        Assertions.assertAll(
                () -> assertNotNull(response.id()),
                () -> assertEquals(0, response.animalsCount()),

                () -> assertEquals(request.capacity(), response.capacity()),
                () -> assertEquals(request.size(), response.size()),
                () -> assertEquals(request.type(), response.type())
        );
    }

    @Test
    @DisplayName("Обновление данных о вольере")
    void updateEnclosure_ValidData() throws Exception {
        var enclosure = enclosureFactory.createEnclosure("TestType", 100, 1000);
        enclosure = enclosureRepository.save(enclosure);

        enclosure.setCapacity(5);
        enclosure.setType("string");
        enclosure.setSize(45);

        var request = EnclosureUpdateRequest.builder()
                                            .id(enclosure.getId())
                                            .capacity(enclosure.getCapacity())
                                            .size(enclosure.getSize())
                                            .type(enclosure.getType())
                                            .build();

        String responseJson = mockMvc.perform(post("/api/enclosure/update").contentType(MediaType.APPLICATION_JSON)
                                                                           .content(objectMapper.writeValueAsString(
                                                                                   request)))
                                     .andExpect(status().isOk())
                                     .andReturn()
                                     .getResponse()
                                     .getContentAsString();

        var response = objectMapper.readValue(responseJson, EnclosureResponse.class);

        final var fin = enclosure;

        Assertions.assertAll(
                () -> assertEquals(fin.getId(), response.id()),
                () -> assertEquals(fin.getSize(), response.size()),
                () -> assertEquals(fin.getCapacity(), response.capacity()),
                () -> assertEquals(fin.getType(), response.type())
        );
    }


    @Test
    @DisplayName("Удаление вольера")
    void removeEnclosure_ValidData() throws Exception {
        var enclosure = enclosureFactory.createEnclosure("TestType", 100, 1000);
        var tmp = enclosureRepository.save(enclosure);
        mockMvc.perform(delete("/api/enclosure/" + tmp.getId())).andExpect(status().isOk());

        Assertions.assertAll(() -> assertNull(enclosureRepository.getEnclosureById(tmp.getId())));
    }

    @Test
    @DisplayName("Получение вольера")
    void getEnclosure_ValidData() throws Exception {
        var enclosure = enclosureFactory.createEnclosure("TestType", 100, 1000);

        final var tmp = enclosureRepository.save(enclosure);

        var json = mockMvc.perform(get("/api/enclosure/" + tmp.getId()))
                          .andExpect(status().isOk())
                          .andReturn()
                          .getResponse()
                          .getContentAsString();

        var response = objectMapper.readValue(json, EnclosureResponse.class);

        Assertions.assertAll(
                () -> assertEquals(tmp.getId(), response.id()),

                () -> assertEquals(enclosure.getSize(), response.size()),
                () -> assertEquals(enclosure.getCapacity(), response.capacity()),
                () -> assertEquals(enclosure.getType(), response.type())
        );
    }

    @Test
    @DisplayName("Получение всех вольеров")
    void getAllEnclosures_ValidData() throws Exception {
        var enclosure = enclosureFactory.createEnclosure("TestType", 100, 1000);

        enclosureRepository.save(enclosure);

        var json = mockMvc.perform(get("/api/enclosure/all"))
                          .andExpect(status().isOk())
                          .andReturn()
                          .getResponse()
                          .getContentAsString();

        var resp = objectMapper.readValue(json, new TypeReference<List<EnclosureResponse>>() {});

        assertFalse(resp.isEmpty());
    }
}
