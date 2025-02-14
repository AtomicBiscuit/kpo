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
import zoo.domains.predators.Predator;
import zoo.domains.predators.Tiger;
import zoo.domains.predators.Wolf;
import zoo.factories.PredatorFactory;
import zoo.helpers.IntHelper;
import zoo.helpers.StringHelper;

/**
 * Класс для тестирования фабрики хищных животных.
 */
@SpringBootTest(classes = {ZooApplication.class})
public class PredatorFactoryTests {
    @Mock
    IntHelper intHelperMock;

    @Mock
    StringHelper stringHelperMock;

    @InjectMocks
    PredatorFactory factory;

    PredatorFactoryTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("В статической переменной predatorsList должен находиться список из всех хищных животных")
    public void staticPredatorListTest() {
        var provider = new ClassPathScanningCandidateComponentProvider(false);

        provider.addIncludeFilter(new AssignableTypeFilter(Predator.class));

        Set<BeanDefinition> components = provider.findCandidateComponents("zoo/");

        var realNames = components.stream().map(BeanDefinition::getBeanClassName).map(name -> name.split("\\."))
                                  .map(name -> name[name.length - 1]).toList();

        var realNamesSorted = new ArrayList<>(realNames);
        Collections.sort(realNamesSorted);

        var predatorsListSorted = new ArrayList<>(PredatorFactory.predatorsList);
        Collections.sort(predatorsListSorted);

        Assertions.assertEquals(realNamesSorted, predatorsListSorted);
    }

    @Test
    @DisplayName("Для хищников должно считываться два числа - food, number и строка - имя")
    public void readParamsTest() {
        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(6, 0);
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn("wolfy");

        var wolf = factory.createWolf();

        Assertions.assertEquals("wolfy", wolf.getName());
        Assertions.assertEquals(6, wolf.getFood());
        Assertions.assertEquals(0, wolf.getNumber());
    }

    @Test
    @DisplayName("Создание волка со случайными числами в качестве параметров")
    public void createWolfTest() {
        var generator = new Random();
        var correct = new Wolf(generator.nextInt(), generator.nextInt(), "String");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getFood(), correct.getNumber());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.createWolf().toString());
    }

    @Test
    @DisplayName("Создание тигра со случайными числами в качестве параметров")
    public void createTigerTest() {
        var generator = new Random();
        var correct = new Tiger(generator.nextInt(), generator.nextInt(), "Big Kitty");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getFood(), correct.getNumber());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.createTiger().toString());
    }

    @Test
    @DisplayName("Создание волка через метод create со случайными числами в качестве параметров")
    public void createWolfImplicitlyTest() {
        var generator = new Random();
        var correct = new Wolf(generator.nextInt(), generator.nextInt(), "Valera");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getFood(), correct.getNumber());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.create("wolf").toString());
    }

    @Test
    @DisplayName("Создание тигра через метод create со случайными числами в качестве параметров")
    public void createTigerImplicitlyTest() {
        var generator = new Random();
        var correct = new Tiger(generator.nextInt(), generator.nextInt(), "Tigrula");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getFood(), correct.getNumber());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.create("tiger").toString());
    }

    @Test
    @DisplayName("При некорректном имени класса, метод create должен возвращать null")
    public void createImplicitlyWithInvalidClassTest() {
        Assertions.assertNull(factory.create("tiger?"));
    }
}
