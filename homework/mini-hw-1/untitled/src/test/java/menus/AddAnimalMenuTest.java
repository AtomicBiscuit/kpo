package menus;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import zoo.ZooApplication;
import zoo.domains.Animal;
import zoo.domains.Zoo;
import zoo.domains.herbos.Monkey;
import zoo.domains.predators.Wolf;
import zoo.helpers.IntHelper;
import zoo.helpers.StringHelper;
import zoo.menus.AddAnimalMenu;

/**
 * Класс для тестирования меню для добавления животных.
 */
@SpringBootTest(classes = {ZooApplication.class, AddAnimalMenu.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan("zoo")
@ComponentScan("clinic")
public class AddAnimalMenuTest {
    @MockitoSpyBean
    ZooApplication app;

    @MockitoBean
    StringHelper stringHelper;

    @MockitoBean
    IntHelper intHelper;

    @Autowired
    @InjectMocks
    AddAnimalMenu menu;

    PrintStream out;

    OutputStream outputStream;

    /**
     * Метод для предварительной настройки. Перенаправляет вывод в буфер и инициализирует моки.
     */
    @BeforeEach
    public void setUp() {
        Mockito.doNothing().when(app).changeMenu(Mockito.anyString());
        MockitoAnnotations.openMocks(this);
        outputStream = new ByteArrayOutputStream();
        out = new PrintStream(outputStream);
        System.setOut(out);
    }

    @Test
    @DisplayName("Проверка корректности вывода меню")
    public void printTest() {
        var lines = List.of("Choose an animal:", "....Add a Monkey", "....Add a Rabbit", "....Add a Wolf",
                            "....Add a Tiger", "");
        menu.print();
        Assertions.assertEquals(String.join(System.lineSeparator(), lines), outputStream.toString());
    }

    @Test
    @DisplayName("Проверка добавления травоядного животного")
    public void addHerboAnimalTest() {
        var zoo = Mockito.mock(Zoo.class);
        Mockito.doReturn("  monkey  ").when(stringHelper).read(Mockito.anyString());
        Mockito.doReturn(1, 2, 3).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(zoo).when(app).getZoo();

        menu.doLogic();

        var args = ArgumentCaptor.forClass(Animal.class);
        Mockito.verify(zoo, Mockito.atLeast(0)).addAnimal(args.capture());

        Assertions.assertEquals(new Monkey(1, 2, 3, "  monkey  ").toString(), args.getValue().toString());
    }

    @Test
    @DisplayName("Проверка добавления хищного животного")
    public void addPredatorAnimalTest() {
        var zoo = Mockito.mock(Zoo.class);
        Mockito.doReturn("  wolf  ").when(stringHelper).read(Mockito.anyString());
        Mockito.doReturn(1, 2).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(zoo).when(app).getZoo();

        menu.doLogic();

        var args = ArgumentCaptor.forClass(Animal.class);
        Mockito.verify(zoo, Mockito.atLeast(0)).addAnimal(args.capture());

        Assertions.assertEquals(new Wolf(1, 2, "  wolf  ").toString(), args.getValue().toString());
    }

    @Test
    @DisplayName("Проверка добавления несуществующего животного")
    public void addInvalidAnimalTest() {
        var zoo = Mockito.mock(Zoo.class);
        Mockito.doReturn("  chair  ", "  monkey  ", "  chairMonkey  ").when(stringHelper).read(Mockito.anyString());
        Mockito.doReturn(1, 2, 3).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(zoo).when(app).getZoo();

        menu.doLogic();

        Assertions.assertEquals("Get unknown animal, try again", outputStream.toString().substring(0, 29));
    }
}
