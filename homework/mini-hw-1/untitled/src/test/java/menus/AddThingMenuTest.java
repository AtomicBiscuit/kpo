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
import zoo.domains.Thing;
import zoo.domains.Zoo;
import zoo.domains.things.Computer;
import zoo.domains.things.Table;
import zoo.helpers.IntHelper;
import zoo.helpers.StringHelper;
import zoo.menus.AddThingMenu;

/**
 * Класс для тестирования меню для добавления вещей.
 */
@SpringBootTest(classes = {ZooApplication.class, AddThingMenu.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan("zoo")
@ComponentScan("clinic")
public class AddThingMenuTest {
    @MockitoSpyBean
    ZooApplication app;

    @MockitoBean
    StringHelper stringHelper;

    @MockitoBean
    IntHelper intHelper;

    @Autowired
    @InjectMocks
    AddThingMenu menu;

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
        var lines = List.of("Choose a thing:", "....Add a Computer", "....Add a Table", "");
        menu.print();
        Assertions.assertEquals(String.join(System.lineSeparator(), lines), outputStream.toString());
    }

    @Test
    @DisplayName("Проверка добавления компьютера")
    public void addComputerTest() {
        var zoo = Mockito.mock(Zoo.class);
        Mockito.doReturn("  ComputeR  ").when(stringHelper).read(Mockito.anyString());
        Mockito.doReturn(999).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(zoo).when(app).getZoo();

        menu.doLogic();

        var args = ArgumentCaptor.forClass(Thing.class);
        Mockito.verify(zoo, Mockito.atLeast(0)).addThing(args.capture());

        Assertions.assertEquals(new Computer(999, "  ComputeR  ") {}.toString(), args.getValue().toString());
    }

    @Test
    @DisplayName("Проверка добавления стола")
    public void addPredatorAnimalTest() {
        var zoo = Mockito.mock(Zoo.class);
        Mockito.doReturn("Table").when(stringHelper).read(Mockito.anyString());
        Mockito.doReturn(0).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(zoo).when(app).getZoo();

        menu.doLogic();

        var args = ArgumentCaptor.forClass(Thing.class);
        Mockito.verify(zoo, Mockito.atLeast(0)).addThing(args.capture());

        Assertions.assertEquals(new Table(0, "Table").toString(), args.getValue().toString());
    }

    @Test
    @DisplayName("Проверка добавления несуществующего предмета")
    public void addInvalidAnimalTest() {
        var zoo = Mockito.mock(Zoo.class);
        Mockito.doReturn("  Human  ", "  table  ", "  notebook  ").when(stringHelper).read(Mockito.anyString());
        Mockito.doReturn(1, 2, 3).when(intHelper).read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.doReturn(zoo).when(app).getZoo();

        menu.doLogic();

        Assertions.assertEquals("Get unknown item, try again", outputStream.toString().substring(0, 27));
    }
}
