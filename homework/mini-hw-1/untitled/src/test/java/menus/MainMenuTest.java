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
import zoo.domains.Thing;
import zoo.domains.herbos.Monkey;
import zoo.domains.predators.Tiger;
import zoo.domains.things.Computer;
import zoo.helpers.IntHelper;
import zoo.menus.MainMenu;

/**
 * Класс для тестирования главного меню.
 */
@SpringBootTest(classes = {ZooApplication.class, MainMenu.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan("zoo")
@ComponentScan("clinic")
public class MainMenuTest {
    @MockitoSpyBean
    ZooApplication app;

    @MockitoBean
    IntHelper intHelper;

    @Autowired
    @InjectMocks
    MainMenu menu;

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
        var lines = List.of("Choose an action:", "    1.   Add new animal", "    2.   Add new thing",
                            "    3.   Get report", "    4.   Get daily food consumption",
                            "    5.   Get friendly animals", "    6.   Exit", "");
        menu.print();
        Assertions.assertEquals(String.join(System.lineSeparator(), lines), outputStream.toString());
    }

    @Test
    @DisplayName("Проверка перехода в меню добавления животных")
    public void actionOneTest() {
        Mockito.doReturn(1).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        menu.doLogic();

        var args = ArgumentCaptor.forClass(String.class);
        Mockito.verify(app).changeMenu(args.capture());

        Assertions.assertEquals("AddAnimalMenu", args.getValue());
    }

    @Test
    @DisplayName("Проверка перехода в меню добавления вещей")
    public void actionTwoTest() {
        Mockito.doReturn(2).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        menu.doLogic();

        var args = ArgumentCaptor.forClass(String.class);
        Mockito.verify(app).changeMenu(args.capture());

        Assertions.assertEquals("AddThingMenu", args.getValue());
    }

    @Test
    @DisplayName("Проверка составления отчёта")
    public void actionThreeTest() {
        Thing thing = new Computer(14, "Computer");
        app.getZoo().addThing(thing);

        Mockito.doReturn(3).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        menu.doLogic();

        var args = ArgumentCaptor.forClass(String.class);
        Mockito.verify(app, Mockito.atLeast(0)).changeMenu(args.capture());

        Assertions.assertEquals("zoo.menus.MainMenu", app.getMenu().getClass().getName());
        var sep = System.lineSeparator();
        Assertions.assertEquals("List of all animals:" + sep + "List of all things:" + sep + thing.toString() + sep,
                                outputStream.toString());
    }

    @Test
    @DisplayName("Проверка подсчёта общего потребления пищи")
    public void actionFourTest() {
        Animal tiger = new Tiger(14, 17, "Cat");
        Animal monkey = new Monkey(100, 425, 1, "Hum");
        while (!app.getZoo().addAnimal(tiger)) {
        }
        while (!app.getZoo().addAnimal(monkey)) {
        }

        Mockito.doReturn(4).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        menu.doLogic();

        var args = ArgumentCaptor.forClass(String.class);
        Assertions.assertEquals("Daily consumption is " + 114 + System.lineSeparator(), outputStream.toString());
    }

    @Test
    @DisplayName("Проверка выбора добрых животных")
    public void actionFiveTest() {
        Animal tiger = new Tiger(14, 17, "Cat");
        Animal monkey = new Monkey(11, 42, 6, "Hum");
        Animal rabbit = new Monkey(91, 12, 4, "Carrotline");
        while (!app.getZoo().addAnimal(tiger)) {
        }
        while (!app.getZoo().addAnimal(monkey)) {
        }
        while (!app.getZoo().addAnimal(rabbit)) {
        }

        Mockito.doReturn(5).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        menu.doLogic();

        var args = ArgumentCaptor.forClass(String.class);
        var sep = System.lineSeparator();
        Assertions.assertEquals("Friendly animals are:" + sep + monkey.toString() + sep, outputStream.toString());
    }
}
