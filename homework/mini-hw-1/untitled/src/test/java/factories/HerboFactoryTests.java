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
import zoo.domains.herbos.Herbo;
import zoo.domains.herbos.Monkey;
import zoo.domains.herbos.Rabbit;
import zoo.factories.HerboFactory;
import zoo.helpers.IntHelper;
import zoo.helpers.StringHelper;

/**
 * Класс для тестирования фабрики травоядных животных.
 */
@SpringBootTest(classes = {ZooApplication.class})
public class HerboFactoryTests {
    @Mock
    IntHelper intHelperMock;

    @Mock
    StringHelper stringHelperMock;

    @InjectMocks
    HerboFactory factory;

    HerboFactoryTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("В статической переменной herbosList должен находиться список из всех травоядных животных")
    public void staticHerboListTest() {
        var provider = new ClassPathScanningCandidateComponentProvider(false);

        provider.addIncludeFilter(new AssignableTypeFilter(Herbo.class));

        Set<BeanDefinition> components = provider.findCandidateComponents("zoo/");

        var realNames = components.stream().map(BeanDefinition::getBeanClassName).map(name -> name.split("\\."))
                                  .map(name -> name[name.length - 1]).toList();

        var realNamesSorted = new ArrayList<>(realNames);
        Collections.sort(realNamesSorted);

        var herbosListSorted = new ArrayList<>(HerboFactory.herbosList);
        Collections.sort(herbosListSorted);

        Assertions.assertEquals(realNamesSorted, herbosListSorted);
    }

    @Test
    @DisplayName("Для травоядных должно считываться три числа - food, number и kindness и строка - имя")
    public void readParamsTest() {
        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.eq(0), Mockito.eq(1_000_000_000)))
               .thenReturn(6, 0);
        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.eq(0), Mockito.eq(10))).thenReturn(9);
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn("monkey`s NAME");

        var monkey = factory.createMonkey();

        Assertions.assertEquals("monkey`s NAME", monkey.getName());
        Assertions.assertEquals(6, monkey.getFood());
        Assertions.assertEquals(0, monkey.getNumber());
        Assertions.assertEquals(9, monkey.getKindness());
    }

    @Test
    @DisplayName("Создание обезьяны со случайными числами в качестве параметров")
    public void createMonkeyTest() {
        var generator = new Random();
        var correct = new Monkey(generator.nextInt(), generator.nextInt(), generator.nextInt(), "String");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getFood(), correct.getNumber(), correct.getKindness());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.createMonkey().toString());
    }

    @Test
    @DisplayName("Создание кролика со случайными числами в качестве параметров")
    public void createRabbitTest() {
        var generator = new Random();
        var correct = new Rabbit(generator.nextInt(), generator.nextInt(), generator.nextInt(), "Shloppa");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getFood(), correct.getNumber(), correct.getKindness());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.createRabbit().toString());
    }

    @Test
    @DisplayName("Создание обезьяны через метод create со случайными числами в качестве параметров")
    public void createMonkeyImplicitlyTest() {
        var generator = new Random();
        var correct = new Monkey(generator.nextInt(), generator.nextInt(), generator.nextInt(), "Shloppa");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getFood(), correct.getNumber(), correct.getKindness());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.create("monkey").toString());
    }

    @Test
    @DisplayName("Создание кролика через метод create со случайными числами в качестве параметров")
    public void createRabbitImplicitlyTest() {
        var generator = new Random();
        var correct = new Rabbit(generator.nextInt(), generator.nextInt(), generator.nextInt(), "Shloppa");

        Mockito.when(intHelperMock.read(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(correct.getFood(), correct.getNumber(), correct.getKindness());
        Mockito.when(stringHelperMock.read(Mockito.anyString())).thenReturn(correct.getName());

        Assertions.assertEquals(correct.toString(), factory.create("rabbit").toString());
    }

    @Test
    @DisplayName("При некорректном имени класса, метод create должен возвращать null")
    public void createImplicitlyWithInvalidClassTest() {
        Assertions.assertNull(factory.create("RABBIT"));
    }
}
