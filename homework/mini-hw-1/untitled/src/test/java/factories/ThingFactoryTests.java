package factories;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import zoo.ZooApplication;
import zoo.domains.Thing;
import zoo.domains.things.Computer;
import zoo.domains.things.Table;
import zoo.factories.ThingFactory;
import zoo.helpers.IntHelper;
import zoo.helpers.StringHelper;

/**
 * Класс для тестирования фабрики вещей.
 */
@SpringBootTest(classes = {ZooApplication.class})
public class ThingFactoryTests {
    @Mock
    IntHelper intHelperMock;

    @Mock
    StringHelper stringHelperMock;

    @InjectMocks
    ThingFactory factory;

    ThingFactoryTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("В статической переменной thingsList должен находиться список из всех вещей")
    public void staticThingsListTest() {
        var provider = new ClassPathScanningCandidateComponentProvider(false);

        provider.addIncludeFilter(new AssignableTypeFilter(Thing.class));

        Set<BeanDefinition> components = provider.findCandidateComponents("zoo/");

        var realNames = components.stream().map(BeanDefinition::getBeanClassName).map(name -> name.split("\\."))
                                  .map(name -> name[name.length - 1]).toList();

        var realNamesSorted = new ArrayList<>(realNames);
        Collections.sort(realNamesSorted);

        var thingsListSorted = new ArrayList<>(ThingFactory.thingsList);
        Collections.sort(thingsListSorted);

        Assertions.assertEquals(realNamesSorted, thingsListSorted);
    }

    @Test
    @DisplayName("Для вещей должно считываться число - number и строка - имя")
    public void readParamsTest() {
        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(11);
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn("Compilator");

        var computer = factory.createComputer();

        Assertions.assertEquals("Compilator", computer.getName());
        Assertions.assertEquals(11, computer.getNumber());
    }

    @Test
    @DisplayName("Создание компьютера со случайными числами в качестве параметров")
    public void createComputerTest() {
        var generator = new Random();
        var correct = new Computer(generator.nextInt(), "Windly System");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getNumber());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.createComputer().toString());
    }

    @Test
    @DisplayName("Создание стола со случайными числами в качестве параметров")
    public void createTableTest() {
        var generator = new Random();
        var correct = new Table(generator.nextInt(), "Iron Table");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getNumber());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.createTable().toString());
    }

    @Test
    @DisplayName("Создание компьютера через метод create со случайными числами в качестве параметров")
    public void createComputerImplicitlyTest() {
        var generator = new Random();
        var correct = new Computer(generator.nextInt(), "Linux based system");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getNumber());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.create("computer").toString());
    }

    @Test
    @DisplayName("Создание стола через метод create со случайными числами в качестве параметров")
    public void createTableImplicitlyTest() {
        var generator = new Random();
        var correct = new Table(generator.nextInt(), "Comfortable");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getNumber());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.create("table").toString());
    }

    @Test
    @DisplayName("При некорректном имени класса, метод create должен возвращать null")
    public void createImplicitlyWithInvalidClassTest() {
        Assertions.assertNull(factory.create("small table"));
    }
}
